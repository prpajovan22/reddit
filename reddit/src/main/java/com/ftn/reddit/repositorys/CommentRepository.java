package com.ftn.reddit.repositorys;

import com.ftn.reddit.DTO.PostDTO;
import com.ftn.reddit.model.Comment;
import com.ftn.reddit.model.Community;
import com.ftn.reddit.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByPost(Post post);

    List<Comment> findByPostAndTextContainingIgnoreCase(Post post, String text);

    List<Comment> findByParentComment(Comment parentComment);

    List<Comment> findByTextContainingIgnoreCase(String text);

}
