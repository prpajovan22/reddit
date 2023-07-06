package ftn.com.reddit.Interface;

import ftn.com.reddit.models.Users;

import java.util.List;

public interface UserInterface {

    List<Users> findAll();

    Users save(Users users);

    Users findByUsername(String username);

    Users findById(Integer id);

    void delete(Users users);
}
