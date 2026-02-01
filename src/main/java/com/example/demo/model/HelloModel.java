package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name= "users")
public class HelloModel {
    @Id
    private Integer id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @Email(message = "Invalid Email")
    private String email;

    public HelloModel(){

    }

    public HelloModel(Integer id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public void setId(Integer id){
        this.id=id;
    }

    public Integer getId() {
        return id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
