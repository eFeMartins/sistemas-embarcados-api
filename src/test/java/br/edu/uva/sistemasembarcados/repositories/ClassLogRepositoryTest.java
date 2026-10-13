package br.edu.uva.sistemasembarcados.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.edu.uva.sistemasembarcados.model.ClassLog;
import br.edu.uva.sistemasembarcados.model.Classroom;
import br.edu.uva.sistemasembarcados.model.Schedule;
import br.edu.uva.sistemasembarcados.model.enums.DayOfWeek;

@DataJpaTest
public class ClassLogRepositoryTest {

    @Autowired
    private ClassLogRepository classLogRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Test
    public void savingAndSearching() {
        Schedule schedule1 = new Schedule(
                DayOfWeek.MONDAY,
                LocalTime.of(10, 10),
                LocalTime.of(12, 10),
                null, null, null
        );

        Classroom class1 = new Classroom("A101", "XID1");

        schedule1 = scheduleRepository.save(schedule1);
        class1 = classroomRepository.save(class1);

        ClassLog log1 = new ClassLog(
                LocalDate.now(),
                LocalDateTime.now(),
                null, null
        );

        ClassLog log2 = new ClassLog(
                LocalDate.of(2026, 10, 1),
                LocalDateTime.of(2026, 10, 1, 11, 30),
                schedule1, class1
        );

        ClassLog logA = classLogRepository.save(log1);
        ClassLog logB = classLogRepository.save(log2);

        assertThat(classLogRepository.findById(logA.getId()))
                .isPresent();

        assertThat(classLogRepository.findById(logB.getId()))
                .isPresent();
    }

    @Test
    public void searchingBySchedule() {
        Schedule schedule1 = new Schedule(
                DayOfWeek.MONDAY,
                LocalTime.of(10, 10),
                LocalTime.of(12, 10),
                null, null, null
        );

        Classroom class1 = new Classroom("A101", "XID1");

        schedule1 = scheduleRepository.save(schedule1);
        class1 = classroomRepository.save(class1);

        ClassLog log = new ClassLog(
                LocalDate.of(2026, 10, 1),
                LocalDateTime.of(2026, 10, 1, 11, 30),
                schedule1, class1
        );

        classLogRepository.save(log);

        List<ClassLog> logs =
                classLogRepository.findByScheduleId(schedule1.getId());

        assertThat(logs).hasSize(1);
        assertThat(logs.get(0).getSchedule().getId())
                .isEqualTo(schedule1.getId());
    }

    @Test
    public void searchingByTimeAndDeleting() {
        Schedule schedule1 = new Schedule(
                DayOfWeek.MONDAY,
                LocalTime.of(10, 10),
                LocalTime.of(12, 10),
                null, null, null
        );

        Classroom class1 = new Classroom("A101", "XID1");

        schedule1 = scheduleRepository.save(schedule1);
        class1 = classroomRepository.save(class1);

        ClassLog log = new ClassLog(
                LocalDate.of(2026, 10, 1),
                LocalDateTime.of(2026, 10, 1, 11, 30),
                schedule1, class1
        );

        log = classLogRepository.save(log);

        ClassLog foundLog = classLogRepository
                .findByScheduleIdAndReadTimeStampBetween(
                        schedule1.getId(),
                        LocalDateTime.of(2026, 10, 1, 10, 30),
                        LocalDateTime.of(2026, 10, 2, 11, 30)
                )
                .orElseThrow(() ->
                        new AssertionError("ClassLog não encontrado"));

        assertThat(foundLog.getId()).isEqualTo(log.getId());

        classLogRepository.deleteById(foundLog.getId());

        assertThat(classLogRepository.findById(foundLog.getId()))
                .isNotPresent();
    }

    @Test
    public void handlingClassLog() {
        Classroom class1 = new Classroom("A101", "XID1");

        // Salva a sala antes de associá-la ao Schedule
        class1 = classroomRepository.save(class1);

        Schedule schedule1 = new Schedule(
                DayOfWeek.THURSDAY,
                LocalTime.of(10, 10),
                LocalTime.of(12, 10),
                null, null, class1
        );

        schedule1 = scheduleRepository.save(schedule1);

        ClassLog log1 = new ClassLog(
                LocalDate.now(),
                LocalDateTime.now(),
                null, null
        );

        ClassLog log2 = new ClassLog(
                LocalDate.of(2026, 10, 1),
                LocalDateTime.of(2026, 10, 1, 10, 0),
                schedule1, class1
        );

        log1 = classLogRepository.save(log1);
        log2 = classLogRepository.save(log2);

        assertThat(classLogRepository.findById(log1.getId()))
                .isPresent();

        assertThat(classLogRepository.findById(log2.getId()))
                .isPresent();

        List<ClassLog> logs =
                classLogRepository.findByScheduleId(schedule1.getId());

        assertThat(logs).hasSize(1);
        assertThat(logs.get(0).getSchedule().getId())
                .isEqualTo(schedule1.getId());

        ClassLog foundLog = classLogRepository
                .findByScheduleIdAndReadTimeStampBetween(
                        schedule1.getId(),
                        LocalDateTime.of(2026, 10, 1, 10, 0),
                        LocalDateTime.of(2026, 10, 1, 13, 0)
                )
                .orElseThrow(() ->
                        new AssertionError("ClassLog não encontrado"));

        assertThat(foundLog.getId()).isEqualTo(log2.getId());

        classLogRepository.deleteById(log1.getId());

        assertThat(classLogRepository.findById(log1.getId()))
                .isNotPresent();
    }
}