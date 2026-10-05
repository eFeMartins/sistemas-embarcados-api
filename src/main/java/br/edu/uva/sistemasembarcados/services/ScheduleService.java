package br.edu.uva.sistemasembarcados.services;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.uva.sistemasembarcados.model.Schedule;
import br.edu.uva.sistemasembarcados.repositories.ScheduleRepository;
import br.edu.uva.sistemasembarcados.services.execptions.DuplicateResourceException;
import br.edu.uva.sistemasembarcados.services.execptions.ResourceNotFoundException;

@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Transactional(readOnly = true)
    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Schedule findById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found with ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<Schedule> findByUserId(Long userId) {
        return scheduleRepository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<Schedule> findByClassroomId(Long classroomId) {
        return scheduleRepository.findByUsualClassroomId(classroomId);
    }

    @Transactional(readOnly = true)
    public List<Schedule> findBySubjectId(Long subjectId) {
        return scheduleRepository.findBySubjectId(subjectId);
    }

    @Transactional
    public Schedule save(Schedule schedule) {
        validateScheduleTimes(schedule.getStartTime(), schedule.getEndTime());
        validateTimeConflicts(schedule, null);
        return scheduleRepository.save(schedule);
    }

    @Transactional
    public Schedule update(Long id, Schedule scheduleDetails) {
        Schedule existingSchedule = findById(id);

        LocalTime newStartTime = scheduleDetails.getStartTime() != null ? scheduleDetails.getStartTime() : existingSchedule.getStartTime();
        LocalTime newEndTime = scheduleDetails.getEndTime() != null ? scheduleDetails.getEndTime() : existingSchedule.getEndTime();

        validateScheduleTimes(newStartTime, newEndTime);

        if (scheduleDetails.getDayOfWeek() != null) {
            existingSchedule.setDayOfWeek(scheduleDetails.getDayOfWeek());
        }
        existingSchedule.setStartTime(newStartTime);
        existingSchedule.setEndTime(newEndTime);

        if (scheduleDetails.getUser() != null) {
            existingSchedule.setUser(scheduleDetails.getUser());
        }
        if (scheduleDetails.getSubject() != null) {
            existingSchedule.setSubject(scheduleDetails.getSubject());
        }
        if (scheduleDetails.getUsualRoom() != null) {
            existingSchedule.setUsualRoom(scheduleDetails.getUsualRoom());
        }

        validateTimeConflicts(existingSchedule, id);

        return scheduleRepository.save(existingSchedule);
    }

    @Transactional
    public void deleteById(Long id) {
        Schedule schedule = findById(id);
        scheduleRepository.delete(schedule);
    }

    private void validateScheduleTimes(LocalTime startTime, LocalTime endTime) {
        if (startTime != null && endTime != null && !endTime.isAfter(startTime)) {
            throw new IllegalArgumentException("schedule end time must be after start time");
        }
    }

    private void validateTimeConflicts(Schedule schedule, Long currentId) {
        // valida se o professor ja possui aula no mesmo dia/horario
        if (schedule.getUser() != null && schedule.getDayOfWeek() != null) {
            List<Schedule> userSchedules = scheduleRepository.findByUserId(schedule.getUser().getId());
            for (Schedule s : userSchedules) {
                if (!s.getId().equals(currentId) && s.getDayOfWeek() == schedule.getDayOfWeek()) {
                    if (hasTimeOverlap(schedule.getStartTime(), schedule.getEndTime(), s.getStartTime(), s.getEndTime())) {
                        throw new DuplicateResourceException("professor already has a schedule allocated at this time");
                    }
                }
            }
        }

        // valida se a sala já está ocupada no mesmo dia/horario
        if (schedule.getUsualRoom() != null && schedule.getDayOfWeek() != null) {
            List<Schedule> classroomSchedules = scheduleRepository.findByUsualClassroomId(schedule.getUsualRoom().getId());
            for (Schedule s : classroomSchedules) {
                if (!s.getId().equals(currentId) && s.getDayOfWeek() == schedule.getDayOfWeek()) {
                    if (hasTimeOverlap(schedule.getStartTime(), schedule.getEndTime(), s.getStartTime(), s.getEndTime())) {
                        throw new DuplicateResourceException("classroom is already assigned to another class at this time");
                    }
                }
            }
        }
    }

    private boolean hasTimeOverlap(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        return start1.isBefore(end2) && start2.isBefore(end1);
    }
}