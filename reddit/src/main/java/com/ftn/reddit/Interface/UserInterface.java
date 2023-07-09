package com.ftn.reddit.Interface;

import com.ftn.reddit.model.Users;

import java.util.List;

public interface UserInterface {

    List<Users> findAll();

    Users save(Users users);

    Users findByUsername(String username);

    Users findById(Integer id);

    void delete(Users users);
}
