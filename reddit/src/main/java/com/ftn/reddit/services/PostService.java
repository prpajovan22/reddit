package com.ftn.reddit.services;

import com.ftn.reddit.Interface.PostInterface;
import com.ftn.reddit.model.Post;
import com.ftn.reddit.repositorys.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService implements PostInterface {

    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Post findById(Integer id) {
        return postRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Post post) {
        postRepository.delete(post);
    }

    @Override
    public Post getPostByUsers_User_id(Integer id) {
        //return postRepository.getPostByUsers_User_id(id);
        return null;
    }

    @Override
    public Post getPostByCommunity_Community_id(Integer id) {
        //return postRepository.getPostByCommunity_Community_id(id);
        return null;
    }

}
