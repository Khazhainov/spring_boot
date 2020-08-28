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
public class AppController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin")
    public String allUsers(ModelMap model) {
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("authUser", authUser);
        model.addAttribute("newUser", new User());
        model.addAttribute("users", userService.listUsers());
        return "admin";
    }

    @GetMapping(value = "/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/admin/add")
    public String saveCustomer(@ModelAttribute("user") User user, @RequestParam("role") String role) {
        user.setRoles(setNewRoles(role));
        userService.add(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/edit")
    public String edit(@RequestParam("id") Long id, @RequestParam("name") String name,
                       @RequestParam("lastName") String lastName, @RequestParam("email") String email,
                       @RequestParam("password") String password, @RequestParam("role") String role) {
        User user = userService.getById(id);
        user.setFirstName(name);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setRoles(setNewRoles(role));
        userService.add(user);
        return "redirect:/admin";
    }

    private Set<Role> setNewRoles(String newRoles) {
        Set<Role> roles = new HashSet<>();
        if (newRoles.contains(", ")) {
            roles.add(userService.getByRoleName("ROLE_USER"));
            roles.add(userService.getByRoleName("ROLE_ADMIN"));
        } else {
            roles.add(userService.getByRoleName(newRoles));
        }
        return roles;
    }


    @RequestMapping("/admin/delete")
    public String deleteUserForm(@RequestParam("id") long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }

    @GetMapping(value = "/user")
    public String helloUser(Model model) {
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("authUser", authUser);
        return "user";
    }
}
