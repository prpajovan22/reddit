package com.ftn.reddit.services;

import com.ftn.reddit.DTO.CommentDTO;
import com.ftn.reddit.DTO.PostDTO;
import com.ftn.reddit.Interface.CommentInterface;
import com.ftn.reddit.model.Comment;
import com.ftn.reddit.model.Community;
import com.ftn.reddit.model.Post;
import com.ftn.reddit.model.pretraga.CommentSerachCriteria;
import com.ftn.reddit.repositorys.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService implements CommentInterface {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment findById(Integer id) {
        return commentRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }

    public List<Comment> getCommentsByPost(Post post) {
        return commentRepository.findByPost(post);
    }

    public List<CommentDTO> findCommentDTOsByPost(Post post) {
        List<Comment> comments = commentRepository.findByPost(post);
        List<CommentDTO> commentDTOs = new ArrayList<>();

        for (Comment comment : comments) {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setComment_id(comment.getComment_id());
            commentDTO.setText(comment.getText());
            commentDTO.setTimeStamp(comment.getTimestamp());
            commentDTO.setCommentDTO(commentDTO.getCommentDTO());
            commentDTO.setPostDTO(commentDTO.getPostDTO());
            commentDTO.setUsersDTO(commentDTO.getUsersDTO());

            commentDTOs.add(commentDTO);
        }

        return commentDTOs;
    }

    public List<Comment> searchCommentsInPost(Post post, CommentSerachCriteria searchCriteria) {
        return commentRepository.findByPostAndTextContainingIgnoreCase(post, searchCriteria.getText());
    }

    public List<Comment> getRepliesForComment(Comment comment) {
        return commentRepository.findByParentComment(comment);
    }

    public List<Comment> searchCommentsByTextContainingIgnoreCase(String text) {
        return commentRepository.findByTextContainingIgnoreCase(text);
    }

}
