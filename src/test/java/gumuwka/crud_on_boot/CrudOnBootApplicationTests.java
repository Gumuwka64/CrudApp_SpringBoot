package gumuwka.crud_on_boot;

import gumuwka.crud_on_boot.model.User;
import gumuwka.crud_on_boot.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CrudOnBootApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveAndFindUser() {
        User user = new User("test", "test@mail.com");
        userRepository.save(user);

        User found = userRepository.findById(user.getId()).orElseThrow();
        assertEquals("test", found.getUsername());
    }

    @Test
    void clearUsers() {
        userRepository.deleteAll();
        assertEquals(0, userRepository.findAll().size());
    }

    @Test
    void contextLoads() {
    }

}
