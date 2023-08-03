package com.ftn.reddit.services;

import com.ftn.reddit.DTO.UsersDTO;
import com.ftn.reddit.Interface.UserInterface;
import com.ftn.reddit.model.Users;
import com.ftn.reddit.repositorys.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserInterface {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public List<Users> findAll() {
        return usersRepository.findAll();
    }

    @Override
    public Users save(Users users) {
        return usersRepository.save(users);
    }


    @Override
    public Users findByUsername(String username) {
        return usersRepository.findUsersByUsername(username).orElse(null);
    }

    @Override
    public Users findById(Integer id) {
        return usersRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Users users) {
        usersRepository.delete(users);
    }

    /*@Override
    public Users saveDTO(UsersDTO usersDTO) {
        return usersRepository.save(usersDTO);
    }*/
}
