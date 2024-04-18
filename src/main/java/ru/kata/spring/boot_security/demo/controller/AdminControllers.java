package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping
public class AdminControllers {

    private final UserService userService;
    private final RoleService roleService;

    public AdminControllers(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin")
    public String allUsers(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName()).get();
        model.addAttribute("user", user);
        model.addAttribute("users", userService.show());
        return "adminPage";
    }

    @GetMapping("/add")
    public String addUser(@ModelAttribute("person") @Validated User user, Model model, Principal principal) {
        User user1 = userService.findByUsername(principal.getName()).get();
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("roles", roles);
        model.addAttribute("r", user1);
        return "addUser";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("person") User user) {
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/view")
    public String show(@RequestParam("id") Long id, Model model) {
        User user = userService.getUserById(id).get();
        model.addAttribute("views", user);
        return "viewForAdmin";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("people", userService.getUserById(id).get());
        return "adminPage";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id).get());
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("rolesss", roles);
        return "adminPage";
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("user") User user, @RequestParam("id") Long id) {
        userService.updateById(id, user);
        return "redirect:/admin";
    }
}
