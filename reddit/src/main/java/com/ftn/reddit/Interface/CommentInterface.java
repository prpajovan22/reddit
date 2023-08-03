package com.ftn.reddit.Interface;

import com.ftn.reddit.model.Comment;

import java.util.List;

public interface CommentInterface {

    List<Comment> findAll();

    Comment save(Comment comment);

    Comment findById(Integer id);

    void delete(Comment comment);
}
