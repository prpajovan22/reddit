package com.ftn.reddit.repositorys;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.reddit.model.Post;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

}
