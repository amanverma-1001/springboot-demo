package com.example.demo.service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.exception.UserAlreadyExistsException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.UserModel;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

   // List<UserModel> UserList = new ArrayList<>();

    private final UserRepository repo;

    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository repo, PasswordEncoder encoder){
        this.repo = repo;
        this.encoder = encoder;
    }

    public UserModel createUser(UserModel user){
        Optional<UserModel> u = repo.findById(user.getId());
        if(!u.isPresent()) {
            user.setPassword(encoder.encode(user.getPassword()));
            repo.save(user);
            log.debug("User creation in service");
            return user;
        } else{
            log.debug("User already exists");
            throw new UserAlreadyExistsException("user Already exists");
        }
    }

    public List<UserModel> getuser(){
       return repo.findAll();
    }

    public Optional<UserModel> getByID(Integer id){
        Optional<UserModel> u = repo.findById(id);
        if(u.isEmpty()){
            log.debug("User doesn't exists");
            throw new UserNotFoundException("user not present");
        } else {
            log.debug("user Found in service");
            return u;
        }
    }


    public UserModel updateById(Integer id, UserModel userReq) {
        UserModel user = repo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found for update"));

        user.setName(userReq.getName());
        user.setEmail(userReq.getEmail());
        log.debug("User updated with id : {}" , id );
        return repo.save(user);
    }

    public void deleteById(Integer id) {
        repo.deleteById(id);
    }

    public UserModel Login(LoginRequest userLogin){
      UserModel user = repo.findUserModelByEmail(userLogin.getEmail()).orElseThrow(() -> new UserNotFoundException("No user found"));
      if(!encoder.matches(userLogin.getPassword(), user.getPassword()))
        {
            throw new UserNotFoundException("Invalid credentials");
        }
     return user;
    }
}
