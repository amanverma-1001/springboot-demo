package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.model.UserModel;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {

     private final UserService userService;

   public UserController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping("/user")
    public ResponseEntity<UserModel> setuser(@Valid @RequestBody UserModel user){
        log.info("Create user request : {} and {} ",user.getEmail() , user.getName());
        UserModel cuser = userService.createUser(user);
        return ResponseEntity.status(201).body(cuser);
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserModel>> getUsers(){
        log.info("Get all users");
        return ResponseEntity.status(202).body(userService.getuser());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Optional<UserModel>> getById(@PathVariable Integer id){
        log.info("Get user for id : {} ", id);
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(userService.getByID(id));
    }

    @PutMapping("/user/{id}")
    public UserModel UpdateById(@PathVariable int id, @RequestBody UserModel user){
        return userService.updateById(id,user);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> DeleteById(@PathVariable Integer id){
         userService.deleteById(id);
         return ResponseEntity.noContent().build();
    }

    @PostMapping("/login")
    public ResponseEntity<String> LoginByUser(@RequestBody LoginRequest userLogin, HttpSession session){
       UserModel user = userService.Login(userLogin);
       session.setAttribute("USER_ID", user.getId());
       return ResponseEntity.status(HttpStatus.valueOf(200)).body("Login successful");
    }

    @GetMapping("/me")
    public Optional<UserModel> me(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("USER_ID");
        if (userId == null) {
            throw new RuntimeException("Not logged in");
        }
        return userService.getByID(userId);
    }


}
