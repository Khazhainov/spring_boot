package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.model.User;
import web.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/rest")
public class RESTController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<Iterable<User>> getUsers() {
        return new ResponseEntity<>(userService.listUsers(), new HttpHeaders(), HttpStatus.OK);
    }

    /*@PostMapping("/add")
    @ResponseBody
    public ResponseEntity<String> addUser(@RequestBody User user) {
        userService.add(user);
        return new ResponseEntity<>(user.getId().toString(), new HttpHeaders(), HttpStatus.OK);
    }*/

    @PostMapping("/add")
    public User addUser(@RequestBody User user) {
        return userService.add(user);
    }
}
