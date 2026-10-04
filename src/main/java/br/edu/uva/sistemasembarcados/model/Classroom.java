package br.edu.uva.sistemasembarcados.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="tb_classromm")
public class Classroom implements Serializable{
	private static final Long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String roomNumber;
	private String boardId;

	public Classroom() {
		
	}
	public Classroom(String roomNumber, String boardId) {
		this.roomNumber = roomNumber;
		this.boardId = boardId;
	}
	
	public String getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	public String getBoardId() {
		return boardId;
	}
	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(roomNumber);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Classroom other = (Classroom) obj;
		return Objects.equals(roomNumber, other.roomNumber);
	}
	
	@Override
	public String toString() {
		return "Classroom [id=" + id + ", roomNumber=" + roomNumber + ", boardId=" + boardId + "]";
	}
	
}
