package com.ftn.reddit.repositorys;

import com.ftn.reddit.model.Comment;
import com.ftn.reddit.model.Post;
import com.ftn.reddit.model.Reaction;
import com.ftn.reddit.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction,Integer> {

    Reaction findByUserAndPost(Users user, Post post);
    List<Reaction> findByPost(Post post);

    Reaction findByUserAndComment(Users user, Comment comment);
}

