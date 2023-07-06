package ftn.com.reddit.repositorys;
import org.springframework.data.jpa.repository.JpaRepository;

import ftn.com.reddit.models.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {

    List<Post> getPostByUser(Integer id);
    List<Post> getPostByUserId(Integer id);

}
