package com.ftn.reddit.repositorys;

import com.ftn.reddit.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users,Integer> {

    Users findUsersByUsername(String username);

    Users findByUsername(String username);

    Optional<Users> findFirstByUsername(String username);
}
