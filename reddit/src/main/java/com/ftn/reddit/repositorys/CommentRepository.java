package com.ftn.reddit.repositorys;

import com.ftn.reddit.model.Comment;
import com.ftn.reddit.model.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
