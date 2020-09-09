package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class RESTController {

    @Autowired
    private UserService userService;

    /*@GetMapping("/all")
    public ResponseEntity<Iterable<User>> getUsers() {
        return new ResponseEntity<>(userService.listUsers(), new HttpHeaders(), HttpStatus.OK);
    }*/

    @GetMapping(value = "/all")
    public List<User> getAllUsers() {
        List<User> users = userService.listUsers();
        return users ;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        userService.add(user);
        return new ResponseEntity<>(user.getId().toString(), new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping(value = "/edit")
    public ResponseEntity<User> edit(@RequestBody User user) {
        if ("".equals(user.getStringRoles())) {
            user.setRoles(userService.getById(user.getId()).getRoles());
        }
        userService.add(user);
        return new ResponseEntity<>(user, new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@RequestBody @PathVariable long id) {
        userService.deleteById(id);
        return new ResponseEntity<>(String.valueOf(id), new HttpHeaders(), HttpStatus.OK);
    }
}
