package gumuwka.crud_on_boot.web.controllers;

import gumuwka.crud_on_boot.model.Role;
import gumuwka.crud_on_boot.model.User;
import gumuwka.crud_on_boot.repository.RoleRepository;
import gumuwka.crud_on_boot.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private final UserService userService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    public AdminController(UserService userService, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("/users")
    public String user(Model model) {
        model.addAttribute("users", userService.findAll());
        return "admin/tablesApp";
    }
    @GetMapping("/users/{id}")
    public String user(@PathVariable long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "admin/user-details";
    }
    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable long id, Model model) {
        userService.delete(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        return "admin/userNew";
    }
    @PostMapping("/users/new")
    public String saveUser(@ModelAttribute("user") User user,
                           Model model) {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN").orElseThrow();
        Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(userRole,adminRole));

        userService.save(user);
        return "redirect:users";
    }

}
