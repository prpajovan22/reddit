package com.ftn.reddit.repositorys;
import com.ftn.reddit.model.Community;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.reddit.model.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findByCommunity(Community community);

    List<Post> findByTitleContainingIgnoreCase(String title);

    List<Post> findByTextContainingIgnoreCase(String text);

    List<Post> findByCommunityAndTitleContainingIgnoreCaseAndTextContainingIgnoreCase(
            Community community, String title, String text
    );

}
