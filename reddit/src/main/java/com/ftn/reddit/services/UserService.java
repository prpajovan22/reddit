package com.ftn.reddit.services;

import com.ftn.reddit.DTO.UsersDTO;
import com.ftn.reddit.Interface.UserInterface;
import com.ftn.reddit.model.UserRole;
import com.ftn.reddit.model.Users;
import com.ftn.reddit.repositorys.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserInterface {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    public boolean verifyOldPassword(String username, String oldPassword) {
        Users user = usersRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    public void updatePassword(String username, String newPassword) {
        Users user = usersRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        usersRepository.save(user);
    }

    public boolean switchUserRoleToUser(Integer user_id) {
        Optional<Users> userOptional = usersRepository.findById(user_id);

        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            user.setUserRole(UserRole.USER);

            usersRepository.save(user);
            return true;
        }

        return false;
    }
}
