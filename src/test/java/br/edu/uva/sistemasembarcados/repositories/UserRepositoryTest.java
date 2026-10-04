package br.edu.uva.sistemasembarcados.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.edu.uva.sistemasembarcados.model.User;
import br.edu.uva.sistemasembarcados.model.enums.Role;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void SavingAndSearchingUsers() {
    	
    	User user1 = new User("Jonh Doe", "0000000", "RFID01", Role.PROFESSOR, null);
    	User user2 = new User("Jonh Beltrano", "1111111", "RFID02", Role.ADMIN, null);
        
    	userRepository.saveAll(Arrays.asList(user1, user2));
        
        User userRes1 = userRepository.findById(1L).get();
        
        assertThat(userRes1).isNotNull();
    }
}