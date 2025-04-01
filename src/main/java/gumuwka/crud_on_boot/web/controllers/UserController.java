package gumuwka.crud_on_boot.web.controllers;

import gumuwka.crud_on_boot.model.User;
import gumuwka.crud_on_boot.service.UserService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserController {
    //внедряем usersevice
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/{id}")
    public String getUserPage(@PathVariable long id, Model model,
                              Authentication authentication) {
        User currentUser = userService.findByUsername(authentication.getName());
        if (currentUser.getId() != id)
            throw new AccessDeniedException("У вас нет доступа к этой странице");
        model.addAttribute("user", currentUser);
        return "user/user";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "/user/userUpdate";
    }
    @PostMapping("/update")
    public String updateUser(
            @RequestParam("id") long id,
            @RequestParam("username") String username,
            @RequestParam("email") String email,
            @RequestParam(value = "newPassword", required = false) String newPassword) {
        userService.update(id, username, email, passwordEncoder.encode(newPassword));
        return "redirect: " + id;
    }


    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        userService.delete(id);
        redirectAttributes.addFlashAttribute("message", "User deleted successfully");
        return "redirect:/reg";
    }
}


