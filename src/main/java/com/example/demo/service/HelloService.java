package com.example.demo.service;

import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.HelloModel;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class HelloService {

   // List<HelloModel> UserList = new ArrayList<>();
    private final UserRepository repo;

    public HelloService(UserRepository repo){
        this.repo = repo;
    }

    public HelloModel createUser(HelloModel user){
        Optional<HelloModel> u = repo.findById(user.getId());
        if(!u.isPresent()) {
            repo.save(user);
            log.debug("User createtion in service");
            return user;
        } else{
            log.debug("User already exists");
            throw new UserAlreadyExistsException("user Already exists");
        }
    }

    public List<HelloModel> getuser(){
       return repo.findAll();
    }

    public Optional<HelloModel> getByID(Integer id){
        Optional<HelloModel> u = repo.findById(id);
        if(u.isEmpty()){
            log.debug("User doesn't exists");
            throw new UserNotFoundException("user not present");
        } else {
            log.debug("user Found in service");
            return u;
        }
    }


    public HelloModel updateById(Integer id, HelloModel userReq) {
        HelloModel user = repo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found for update"));

        user.setName(userReq.getName());
        user.setEmail(userReq.getEmail());
        log.debug("User updated with id : {}" , id );
        return repo.save(user);
    }

    public void deleteById(Integer id) {
        repo.deleteById(id);
    }
}
