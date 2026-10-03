package br.edu.uva.sistemasembarcados.model;

import java.util.Objects;

public class Classroom {
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
