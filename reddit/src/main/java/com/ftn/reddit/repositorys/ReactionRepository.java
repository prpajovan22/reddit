package com.ftn.reddit.repositorys;

import com.ftn.reddit.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction,Integer> {

    Reaction findByUserAndPost(Users user, Post post);
    List<Reaction> findByPost(Post post);

    boolean existsByUserAndPostAndType(Users user, Post post, ReactionType type);

    void deleteByPostAndUserAndType(Post post, Users user, ReactionType type);
    void deleteByCommentAndUserAndType(Comment comment, Users user, ReactionType type);

    boolean existsByUserAndCommentAndType(Users user, Comment comment, ReactionType type);

    Reaction findByUserAndComment(Users user, Comment comment);
}

