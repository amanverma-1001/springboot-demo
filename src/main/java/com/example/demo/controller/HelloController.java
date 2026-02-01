package com.example.demo.controller;

import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.model.HelloModel;
import com.example.demo.service.HelloService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
public class HelloController {

     private final HelloService helloService;

   public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }



    @PostMapping("/hello")
    public ResponseEntity<HelloModel> setuser(@Valid @RequestBody HelloModel user){
        log.info("Create user request : {} and {} ",user.getEmail() , user.getName());
        HelloModel cuser = helloService.createUser(user);
        return ResponseEntity.status(201).body(cuser);
    }

    @GetMapping("/hello")
    public ResponseEntity<List<HelloModel>> getUsers(){
        log.info("Get all users");
        return ResponseEntity.status(202).body(helloService.getuser());
    }

    @GetMapping("/hello/{id}")
    public ResponseEntity<Optional<HelloModel>> getById(@PathVariable Integer id){
        log.info("Get user for id : {} ", id);
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(helloService.getByID(id));
    }

    @PutMapping("/hello/{id}")
    public HelloModel UpdateById(@PathVariable int id, @RequestBody HelloModel user){
        return helloService.updateById(id,user);
    }

    @DeleteMapping("/hello/{id}")
    public ResponseEntity<String> DeleteById(@PathVariable Integer id){
         helloService.deleteById(id);
         return ResponseEntity.noContent().build();
    }




}
