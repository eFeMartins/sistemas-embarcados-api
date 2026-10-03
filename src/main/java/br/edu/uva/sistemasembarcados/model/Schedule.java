package br.edu.uva.sistemasembarcados.model;

import java.time.LocalTime;
import java.util.Objects;

import br.edu.uva.sistemasembarcados.model.enums.DayOfWeek;

public class Schedule {
	private Long id;
	private DayOfWeek dayOfWeek;
	private LocalTime startTime;
	private LocalTime endTime;
	
	private User teacher;
	private Subject subject;
	private Classroom usualRoom;
	
	public Schedule() {
	}
	public Schedule(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime endTime, User teacher, Subject subject,
			Classroom usualRoom) {
		this.dayOfWeek = dayOfWeek;
		this.startTime = startTime;
		this.endTime = endTime;
		this.teacher = teacher;
		this.subject = subject;
		this.usualRoom = usualRoom;
	}
	public DayOfWeek getDayOfWeek() {
		return dayOfWeek;
	}
	public void setDayOfWeek(DayOfWeek dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	public LocalTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	public LocalTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
	public User getTeacher() {
		return teacher;
	}
	public void setTeacher(User teacher) {
		this.teacher = teacher;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public Classroom getUsualRoom() {
		return usualRoom;
	}
	public void setUsualRoom(Classroom usualRoom) {
		this.usualRoom = usualRoom;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Schedule other = (Schedule) obj;
		return Objects.equals(id, other.id);
	}
	
	@Override
	public String toString() {
		return "Schedule [id=" + id + ", teacher=" + teacher + ", subject=" + subject + ", usualRoom=" + usualRoom
				+ "]";
	}

	
	
	
}
