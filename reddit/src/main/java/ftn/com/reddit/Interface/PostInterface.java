package ftn.com.reddit.Interface;

import ftn.com.reddit.models.Post;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface PostInterface {

    List<Post> findAll();

    Post save(Post post);

    Post findById(Integer id);

    void delete(Post post);

    List<Post> getByUser(Integer id);

    List<Post> postUser(Integer id);


}
