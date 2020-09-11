package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.model.Role;
import web.model.User;
import web.service.UserService;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/rest")
public class RESTController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<Iterable<User>> getUsers() {
        return new ResponseEntity<>(userService.listUsers(), new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping(value = "/all/{id}")
    public ResponseEntity<User> userPage(@PathVariable long id) {
        return new ResponseEntity<>(userService.getById(id), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody User user, @RequestParam("roles") String role) {
        user.setRoles(setNewRoles(role));
        userService.add(user);
        return new ResponseEntity<>(user.getId().toString(), new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping(value = "/edit")
    public ResponseEntity<User> edit(@RequestBody User user, @RequestParam("roles") String role) {
        user.setRoles(setNewRoles(role));
        userService.add(user);
        return new ResponseEntity<>(user, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@RequestBody @PathVariable long id) {
        userService.deleteById(id);
        return new ResponseEntity<>(String.valueOf(id), new HttpHeaders(), HttpStatus.OK);
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


}
