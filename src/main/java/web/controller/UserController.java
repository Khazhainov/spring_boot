package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String allUsers(ModelMap model) {
        model.addAttribute("users", userService.listUsers());
        return "admin";
    }

    @GetMapping(value = "login")
    public String loginPage() {
        return "login";
    }

    @RequestMapping("/admin/new")
    public String newUserForm(ModelMap model) {
        model.addAttribute("user", new User());
        return "new";
    }

    @GetMapping("/admin/edit/{id}")
    public String editUserForm(@PathVariable("id") long id, ModelMap model) {
        model.addAttribute("user", userService.getById(id));
        return "update";
    }

    @PostMapping(value = "/admin/update/{id}")
    public String editUserPost(@PathVariable("id") Long id, User user, BindingResult result, ModelMap modelMap, @RequestParam("role") String role) {
        if (result.hasErrors()) {
            user.setId(id);
            return "update";
        }
        Set<Role> roles = new HashSet<>();
        if (role.contains(", ")) {
            roles.add(userService.getByRoleName("ROLE_USER"));
            roles.add(userService.getByRoleName("ROLE_ADMIN"));
        } else {
            roles.add(userService.getByRoleName(role));
        }
        user.setRoles(roles);
        userService.add(user);
        modelMap.addAttribute("user", userService.listUsers());
        return "redirect:/admin";
    }

    @RequestMapping("/admin/delete/{id}")
    public String deleteUserForm(@PathVariable("id") long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @PostMapping("/admin/save")
    public String saveCustomer(@ModelAttribute("user") User user) {
        userService.add(user);
        return "redirect:/admin";
    }

    @GetMapping(value = "/user")
    public String helloUser(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", user);
        return "user";
    }
}
