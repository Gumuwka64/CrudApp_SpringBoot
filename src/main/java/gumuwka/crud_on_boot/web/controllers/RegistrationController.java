package gumuwka.crud_on_boot.web.controllers;

import gumuwka.crud_on_boot.model.Role;
import gumuwka.crud_on_boot.model.User;
import gumuwka.crud_on_boot.repository.RoleRepository;
import gumuwka.crud_on_boot.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.Set;

@Controller
public class RegistrationController {
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    public RegistrationController(UserService userService, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

//    @GetMapping("/login")
//    public String login(@RequestParam(value = "error",required = false) String error,
//                        @RequestParam(value ="logout", required = false) String logout,
//                        Model model) {
//        if (error != null) {
//            model.addAttribute("error", error);
//        }
//        if (logout != null) {
//            model.addAttribute("message", "You have been logged out successfully.");
//        }
//        return "login";
//    }

    @GetMapping("/reg")
    public String registration() {
        return "registration";
    }
    @PostMapping("/reg")
    public String newUser(@RequestParam String username,
                          @RequestParam String email,
                          @RequestParam String password,
                          Model model) {
        try {
            Role userRole = roleRepository.findByName("ROLE_USER").orElse(null);
            if (userRole == null) {
                userRole = roleRepository.save(new Role("ROLE_USER"));
            }
            User user = new User(username, email,passwordEncoder.encode(password));
            user.setRoles(Set.of(userRole));
            userService.save(user);
            return "redirect:/login";
        }catch (Exception e){
            model.addAttribute("error","ошибка регистрации: " + e.getMessage());
            return "registration";
        }
    }
}
