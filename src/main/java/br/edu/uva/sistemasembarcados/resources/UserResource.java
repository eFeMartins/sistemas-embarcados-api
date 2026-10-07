package br.edu.uva.sistemasembarcados.resources;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.uva.sistemasembarcados.model.User;
import br.edu.uva.sistemasembarcados.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserResource {
	
	private final UserService service;
	
	public UserResource(UserService service) {
		this.service = service;
	}
	
	@GetMapping
	public ResponseEntity<List<User>> findAll(){
		List<User> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> findById(@PathVariable Long Userid){
		User user = service.findById(Userid);
		return ResponseEntity.ok().body(user);
	}
	
	//funções abaixo devem responder com
	// codigos http via responseEntity e não serem void
	@PostMapping
	public void saveUser(User user){
		service.save(user);
	}
	
	@PutMapping("/{id}")
	public void updateUser(@PathVariable Long userId, @RequestBody User userDetails) {
		service.update(userId, userDetails);
	}
	
	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable Long id) {
		service.findById(id);
	}
	
}
