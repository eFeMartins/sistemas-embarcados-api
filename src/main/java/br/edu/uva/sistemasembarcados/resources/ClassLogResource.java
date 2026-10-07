package br.edu.uva.sistemasembarcados.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.uva.sistemasembarcados.model.ClassLog;
import br.edu.uva.sistemasembarcados.services.ClassLogService;

@RestController
@RequestMapping(value = "/class-logs")
public class ClassLogResource {
	private final ClassLogService service;
	
	public ClassLogResource(ClassLogService service) {
		this.service = service;
	}
	
	@PostMapping("/register")
	public void registerAttendence(String rfidTag, String boardId) {
		service.registerAttendance(rfidTag, boardId);
	}
	
	@GetMapping
	public ResponseEntity<List<ClassLog>> findAll(){
		List<ClassLog> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ClassLog> findById(@PathVariable Long id){
		ClassLog classLog = service.findById(id);
		return ResponseEntity.ok().body(classLog);
	}
	
	@GetMapping("/Schedule/{ScheduleId}")
	public ResponseEntity<List<ClassLog>> findByScheduleId(@PathVariable Long scheduleId){
		List<ClassLog> list= service.findByScheduleId(scheduleId);
		return ResponseEntity.ok().body(list);
	}
	
}
