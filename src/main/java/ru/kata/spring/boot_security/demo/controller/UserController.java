package ru.kata.spring.boot_security.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.servies.RoleService;
import ru.kata.spring.boot_security.demo.servies.UserService;

import java.security.Principal;
import java.util.ArrayList;


@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleService roleService;


    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    @GetMapping("/user")
    public String userPage(Model model, Principal principal) {
        model.addAttribute("user", userService.getUserByName(principal.getName()));
        return "userPage";
    }

    @GetMapping("/admin")
    public String adminPage(Model model, Principal principal) {
        model.addAttribute("admin", userService.getUserByName(principal.getName()));
        return "adminPage";
    }

    @GetMapping("/admin/user_list")
    public String getUsersList(Model model) {
        model.addAttribute("users", userService.allUsers());
        return "/userList";
    }

    @GetMapping("/admin/user_list/{id}")
    public String showUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "/showUser";
    }

    @GetMapping("/admin/new_user")
    public String newUser(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", roleService.allRoles());
        return "/addUser";
    }

    @PostMapping("/admin/new_user")
    public String addUser(@RequestParam ArrayList<Integer> roles, @RequestParam String name,
                          @RequestParam String password, @RequestParam String email) {
        userService.save(roles, name, password, email);
        return "redirect:/admin/user_list";
    }

    @GetMapping("/admin/user_list/{id}/update")
    public String editUser(Model model, @PathVariable("id") Long id) {
        model.addAttribute("roles", roleService.allRoles());
        model.addAttribute("user", userService.getUserById(id));
        return "/updateUser";
    }

    @PostMapping("/admin/user_list/{id}")
    public String updateUser(@RequestParam ArrayList<Integer> roles, @RequestParam String name,
                             @RequestParam String password, @RequestParam String email) {
        userService.update(roles, name, password, email);
        return "redirect:/admin/user_list";
    }

    @DeleteMapping("/admin/user_list/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin/user_list";
    }
}

