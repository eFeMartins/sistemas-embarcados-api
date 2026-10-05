package br.edu.uva.sistemasembarcados.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.uva.sistemasembarcados.model.User;
import br.edu.uva.sistemasembarcados.repositories.UserRepository;
import br.edu.uva.sistemasembarcados.services.execptions.DuplicateResourceException;
import br.edu.uva.sistemasembarcados.services.execptions.ResourceNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Transactional(readOnly = true)
	public List<User> findAll(){
		return userRepository.findAll();
	}
	@Transactional(readOnly = true)
	public User findById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("user by id not found message"));
	}
	@Transactional(readOnly = true)
	public User findByRfidTag(String rfid) {
		return userRepository.findByRfidTag(rfid)
				.orElseThrow(() -> new ResourceNotFoundException("user by rfid not found message"));
	}
	@Transactional(readOnly = true)
	public User findByEmail(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("user by email not found message"));
	}
	@Transactional
	public User save(User user) {
		// usar -> isPresent
		// verificação de invalidos vem primeiro
		
		if (userRepository.findByEmail(user.getEmail()).isPresent() || userRepository.findByRfidTag(user.getRfidTag()).isPresent()) {
			throw new DuplicateResourceException("user email and/or rfid already exists");
		}else {
			userRepository.save(user);
		}
		return user;
	}
	@Transactional
	public User update(Long id, User userDetails) {
	    // 1. Busca o usuário existente
	    User existingUser = userRepository.findById(id)
	            .orElseThrow(() -> new ResourceNotFoundException("User ID: " + id +" not found"));

	    // 2. Valida duplicidade de E-mail (se alterado verifica se já pertence a OUTRO usuário)
	    if (userDetails.getEmail() != null && !userDetails.getEmail().equals(existingUser.getEmail())) {
	        userRepository.findByEmail(userDetails.getEmail()).ifPresent(userWithEmail -> {
	            if (!userWithEmail.getId().equals(id)) {
	                throw new DuplicateResourceException("email already used in other user: " + userDetails.getEmail());
	            }
	        });
	        existingUser.setEmail(userDetails.getEmail());
	    }

	    // 3. Valida duplicidade de RFID Tag (se alterada verifica se já pertence a OUTRO usuário)
	    if (userDetails.getRfidTag() != null && !userDetails.getRfidTag().equals(existingUser.getRfidTag())) {
	        userRepository.findByRfidTag(userDetails.getRfidTag()).ifPresent(userWithRfid -> {
	            if (!userWithRfid.getId().equals(id)) {
	                throw new DuplicateResourceException("RFID already used in other user: " + userDetails.getRfidTag());
	            }
	        });
	        existingUser.setRfidTag(userDetails.getRfidTag());
	    }

	    // 4. Atualiza os demais campos permitidos
	    if (userDetails.getName() != null) {
	        existingUser.setName(userDetails.getName());
	    }
	    if (userDetails.getPassword() != null) {
	        existingUser.setPassword(userDetails.getPassword());
	    }
	    if (userDetails.getRole() != null) {
	        existingUser.setRole(userDetails.getRole());
	    }

	    // 5. Persiste as alterações e retorna o objeto atualizado
	    return userRepository.save(existingUser);
	}
	@Transactional
	public void deleteById(Long id) {
		// code
		userRepository.deleteById(id);
	}
}
