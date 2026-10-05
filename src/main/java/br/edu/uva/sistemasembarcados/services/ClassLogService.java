package br.edu.uva.sistemasembarcados.services;

import br.edu.uva.sistemasembarcados.services.execptions.DuplicateResourceException;
import br.edu.uva.sistemasembarcados.services.execptions.ResourceNotFoundException;
import br.edu.uva.sistemasembarcados.model.ClassLog;
import br.edu.uva.sistemasembarcados.model.Classroom;
import br.edu.uva.sistemasembarcados.model.Schedule;
import br.edu.uva.sistemasembarcados.model.User;
import br.edu.uva.sistemasembarcados.model.enums.DayOfWeek;
import br.edu.uva.sistemasembarcados.repositories.ClassLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class ClassLogService {

    private final ClassLogRepository classLogRepository;
    private final UserService userService;
    private final ClassroomService classroomService;
    private final ScheduleService scheduleService;

    @Autowired
    public ClassLogService(ClassLogRepository classLogRepository, UserService userService, 
                           ClassroomService classroomService, ScheduleService scheduleService) {
        this.classLogRepository = classLogRepository;
        this.userService = userService;
        this.classroomService = classroomService;
        this.scheduleService = scheduleService;
    }

    @Transactional
    public ClassLog registerAttendance(String rfidTag, String boardId) {
        // 1. identifica quem é o professor e onde ele está fisicamente
        User professor = userService.findByRfidTag(rfidTag);
        Classroom actualClassroom = classroomService.findByBoardId(boardId);

        // 2. pega o tempo e data exatos do momento da leitura
        LocalDateTime now = LocalDateTime.now();
        
        java.time.DayOfWeek javaDay = now.getDayOfWeek();
        DayOfWeek currentDay = DayOfWeek.valueOf(javaDay.name());
        
        LocalTime currentTime = now.toLocalTime();
        
        // 3. busca a grade do professor e encontra qual aula esta acontecendo AGORA
        List<Schedule> professorSchedules = scheduleService.findByUserId(professor.getId());
        
        Schedule activeSchedule = professorSchedules.stream()
                .filter(s -> s.getDayOfWeek() == currentDay)
                .filter(s -> isTimeValidForClass(currentTime, s.getStartTime(), s.getEndTime()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No active class schedule found for professor " + professor.getName() + " at this time."));

        // 4. previne duplicidade caso o professor passe o cracha duas vezes na mesma aula
        LocalDate today = now.toLocalDate();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(LocalTime.MAX);
        
        Optional<ClassLog> existingLog = classLogRepository.findByScheduleIdAndTimestampBetween(
                activeSchedule.getId(), startOfDay, endOfDay);

        if (existingLog.isPresent()) {
            throw new DuplicateResourceException("Attendance already registered for this class today.");
        }

        // 5. instancia o log de aula e salva
        ClassLog classLog = new ClassLog();
        classLog.setSchedule(activeSchedule);
        classLog.setActualClassroom(actualClassroom);
        classLog.setReadTimeStamp(now);

        return classLogRepository.save(classLog);
    }

    @Transactional(readOnly = true)
    public List<ClassLog> findAll() {
        return classLogRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ClassLog findById(Long id) {
        return classLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class log not found with ID: " + id));
    }
    
    @Transactional(readOnly = true)
    public List<ClassLog> findByScheduleId(Long scheduleId) {
        return classLogRepository.findByScheduleId(scheduleId);
    }

    /**
     * Valida se a hora atual pertence ao intervalo da aula, permitindo 
     * um registro antecipado de até 20 minutos (buffer).
     */
    private boolean isTimeValidForClass(LocalTime currentTime, LocalTime startTime, LocalTime endTime) {
        LocalTime startTimeWithBuffer = startTime.minusMinutes(20);
        
        // Retorna true se a hora atual for igual ou depois do início (com tolerância) 
        // E antes do término oficial da aula.
        return !currentTime.isBefore(startTimeWithBuffer) && currentTime.isBefore(endTime);
    }
}