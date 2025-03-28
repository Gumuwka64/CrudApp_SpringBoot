package gumuwka.crud_on_boot.web.controllers;

import gumuwka.crud_on_boot.model.User;
import gumuwka.crud_on_boot.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserController {
    //внедряем usersevice
    private UserService userService ;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String user(Model model) {
        model.addAttribute("users", userService.findAll());
        return "tablesApp";
    }

    @GetMapping("/{id}")
    public String user(Model model, @PathVariable("id") long id){
        model.addAttribute("user",userService.findById(id));
        return "user";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "userUpdate";}
    @PostMapping("/update")
    public String updateUser(
            @RequestParam("id") long id,
            @RequestParam("username")  String username,
            @RequestParam("email") String email) {
        userService.update(id, username, email);
        return "redirect:/user/" + id;
    }

    @GetMapping("/new")
    public String showNewUserForm(Model model) {
        model.addAttribute("user", new User());
        return "userNew";}
    @PostMapping("/new")
    public String createUser(@ModelAttribute User user) {
        userService.save(user);
        return "redirect:/user/" + user.getId();
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        userService.delete(id);
        redirectAttributes.addFlashAttribute("message", "User deleted successfully");
        return "redirect:/user/home";
    }

}

