package gumuwka.crud_on_boot.web.init;

import gumuwka.crud_on_boot.model.User;
import gumuwka.crud_on_boot.model.Role;
import gumuwka.crud_on_boot.repository.RoleRepository;
import gumuwka.crud_on_boot.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


//РЕГАЕМ ADMIN ПО УМЛОЧАНИЮ

@Service
public class DataInitializer implements CommandLineRunner {
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserService userService;

    public DataInitializer(PasswordEncoder passwordEncoder, RoleRepository roleRepository, UserService userService) {//
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userService = userService;
    }


    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> roleRepository.save(new Role("admin")));
        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(new Role("user")));
        if(userService.findByUsername("admin") == null & userService.findByEmail("admin@mail.com") == null) {
            User admin = new User("admin","admin@mail.com", passwordEncoder.encode("admin123"));
            admin.setRoles(Set.of(adminRole, userRole));
            userService.save(admin);
    }
        if(userService.findByUsername("demouser")== null & userService.findByEmail("user@mail.ru") == null) {
            User user = new User("demouser", "user@mail.ru",passwordEncoder.encode("demo123"));
            user.setRoles(Set.of(userRole));
            userService.save(user);
    }
}
}
