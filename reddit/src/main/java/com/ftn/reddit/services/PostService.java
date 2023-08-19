package com.ftn.reddit.services;

import com.ftn.reddit.Interface.PostInterface;
import com.ftn.reddit.model.Community;
import com.ftn.reddit.model.Post;
import com.ftn.reddit.model.pretraga.CommunitySearchCriteria;
import com.ftn.reddit.model.pretraga.PostSearchCriteria;
import com.ftn.reddit.repositorys.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

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


    public List<Post> getPostsByCommunity(Community community){
        return postRepository.findByCommunity(community);
    }

    public List<Post> searchPosts(PostSearchCriteria searchCriteria) {
        if (StringUtils.isEmpty(searchCriteria.getTitle()) && StringUtils.isEmpty(searchCriteria.getText())) {
            // Return all posts when both title and text are empty
            return postRepository.findAll();
        }

        List<Post> titleSearchResult = new ArrayList<>();
        List<Post> textSearchResult = new ArrayList<>();

        if (!StringUtils.isEmpty(searchCriteria.getTitle())) {
            titleSearchResult = postRepository.findByTitleContainingIgnoreCase(searchCriteria.getTitle().toLowerCase());
        }

        if (!StringUtils.isEmpty(searchCriteria.getText())) {
            textSearchResult = postRepository.findByTextContainingIgnoreCase(searchCriteria.getText().toLowerCase());
        }

        Set<Post> combinedResult = new HashSet<>(titleSearchResult);
        combinedResult.addAll(textSearchResult);

        return new ArrayList<>(combinedResult);
    }

    public List<Post> searchPostsInCommunity(Community community, PostSearchCriteria searchCriteria) {
        return postRepository.findByCommunityAndTitleContainingIgnoreCaseAndTextContainingIgnoreCase(
                community, searchCriteria.getTitle(), searchCriteria.getText()
        );
    }

}
