package br.edu.uva.sistemasembarcados.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.edu.uva.sistemasembarcados.model.Classroom;
import br.edu.uva.sistemasembarcados.model.Schedule;
import br.edu.uva.sistemasembarcados.model.Subject;
import br.edu.uva.sistemasembarcados.model.User;
import br.edu.uva.sistemasembarcados.model.enums.DayOfWeek;
import br.edu.uva.sistemasembarcados.model.enums.Role;

@DataJpaTest
public class ScheduleRepositoryTest {
	
	@Autowired
	private ScheduleRepository repository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	SubjectRepository subjectRepository;
	
	@Autowired
	ClassroomRepository classroomRepository;
	
	@Test
	public void savingAndSearchingRoom() {

		Classroom classroom = new Classroom("A101", "XID1");
		Subject subject = new Subject("scratch balls 101", "STCHBLLZ");
		User user = new User("Jhon Whom", "Whom@gmail.com", "101010", "WHOMRFID", Role.PROFESSOR, null);
		
		classroom = classroomRepository.save(classroom);
		subject = subjectRepository.save(subject);
		user = userRepository.save(user);
		
		Schedule schedule = new Schedule(
                DayOfWeek.MONDAY,
                LocalTime.of(10, 10),
                LocalTime.of(12, 10),
                user, subject,
                classroom
        );
		repository.save(schedule);
		
		assertThat(repository.findByUsualRoomId(classroom.getId())).isNotEmpty();
		assertThat(repository.findByUsualRoomId(classroom.getId()).get(0).getUsualRoom())
		.isEqualTo(schedule.getUsualRoom());
	}
	
	@Test
	public void searchTeacher() {
		Classroom classroom = new Classroom("A101", "XID1");
		Subject subject = new Subject("scratch balls 101", "STCHBLLZ");
		User user = new User("Jhon Whom", "Whom@gmail.com", "101010", "WHOMRFID", Role.PROFESSOR, null);
		
		classroom = classroomRepository.save(classroom);
		subject = subjectRepository.save(subject);
		user = userRepository.save(user);
		
		Schedule schedule = new Schedule(
                DayOfWeek.MONDAY,
                LocalTime.of(10, 10),
                LocalTime.of(12, 10),
                user, subject,
                classroom
        );
		repository.save(schedule);
		
		assertThat(repository.findByTeacherId(user.getId())).isNotEmpty();
		assertThat(repository.findByTeacherId(user.getId()).get(0).getUser().getId())
		.isEqualTo(user.getId());
	}
	
	@Test
	public void searchSubject() {
		Classroom classroom = new Classroom("A101", "XID1");
		Subject subject = new Subject("scratch balls 101", "STCHBLLZ");
		User user = new User("Jhon Whom", "Whom@gmail.com", "101010", "WHOMRFID", Role.PROFESSOR, null);
		
		classroom = classroomRepository.save(classroom);
		subject = subjectRepository.save(subject);
		user = userRepository.save(user);
		
		Schedule schedule = new Schedule(
                DayOfWeek.MONDAY,
                LocalTime.of(10, 10),
                LocalTime.of(12, 10),
                user, subject,
                classroom
        );
		repository.save(schedule);
		
		assertThat(repository.findBySubjectId(subject.getId())).isNotEmpty();
		assertThat(repository.findBySubjectId(subject.getId()).get(0).getSubject().getCode())
		.isEqualTo(subject.getCode());
	}
//	public List<Schedule> findByTeacherId(Long id);
//	public List<Schedule> findByUsualRoomId(Long usualClassroomId);
//	public List<Schedule> findBySubjectId(Long subjectId);
}
