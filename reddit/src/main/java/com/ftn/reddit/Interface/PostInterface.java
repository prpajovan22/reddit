package com.ftn.reddit.Interface;

import com.ftn.reddit.model.Post;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface PostInterface {

    List<Post> findAll();

    Post save(Post post);

    Post findById(Integer id);

    void delete(Post post);


}
