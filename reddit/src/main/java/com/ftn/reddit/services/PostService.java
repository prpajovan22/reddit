package com.ftn.reddit.services;

import com.ftn.reddit.DTO.PostDTO;
import com.ftn.reddit.DTO.ReactionDTO;
import com.ftn.reddit.Interface.PostInterface;
import com.ftn.reddit.model.*;
import com.ftn.reddit.model.pretraga.CommunitySearchCriteria;
import com.ftn.reddit.model.pretraga.PostSearchCriteria;
import com.ftn.reddit.repositorys.PostRepository;
import com.ftn.reddit.repositorys.ReactionRepository;
import com.ftn.reddit.repositorys.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostService implements PostInterface {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ReactionService reactionService;

    @Autowired
    private ReportService reportService;
    @Autowired
    private ReportRepository reportRepository;

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
        List<Post> posts = new ArrayList<>();
        boolean shouldSearchMore = true;
        if (StringUtils.isEmpty(searchCriteria.getTitle()) && StringUtils.isEmpty(searchCriteria.getText())) {
            posts.addAll(postRepository.findAll());
            shouldSearchMore = false;
        }

        if (!StringUtils.isEmpty(searchCriteria.getTitle()) && shouldSearchMore) {
            posts.addAll(postRepository.findByTitleContainingIgnoreCase(searchCriteria.getTitle().toLowerCase()));
        }

        if (!StringUtils.isEmpty(searchCriteria.getText())&& shouldSearchMore) {
            posts.addAll(postRepository.findByTextContainingIgnoreCase(searchCriteria.getText().toLowerCase()));
        }

        Set<Post> uniqueResault = new HashSet<>(posts);

        if(searchCriteria.getFromReactionCount() > 0 || searchCriteria.getToReactionCount() > 0){
            uniqueResault = uniqueResault.stream().filter(post -> {
                return post.getComments().size() >= searchCriteria.getFromReactionCount() && post.getComments().size() <= searchCriteria.getToReactionCount();
            }).collect(Collectors.toSet());
        }
        if((searchCriteria.getFromCommentCount() != null && searchCriteria.getFromCommentCount() > 0) || (searchCriteria.getToCommentCount() != null && searchCriteria.getToCommentCount() > 0)){
            uniqueResault = uniqueResault.stream().filter(post -> {
                return post.getReactions().size() >= searchCriteria.getFromCommentCount() && post.getReactions().size() <= searchCriteria.getToCommentCount();
            }).collect(Collectors.toSet());
        }
        /*if(searchCriteria.getCommunity_id() > 0){
            uniqueResault = uniqueResault.stream().filter(post -> post.getCommunity().getCommunity_id().equals(searchCriteria.getCommunity_id())).collect(Collectors.toSet());
        }*/
        if (searchCriteria.getCommunity_id() != null && searchCriteria.getCommunity_id() > 0) {
            uniqueResault = uniqueResault.stream()
                    .filter(post -> post.getCommunity() != null &&
                            post.getCommunity().getCommunity_id() != null &&
                            post.getCommunity().getCommunity_id().equals(searchCriteria.getCommunity_id()))
                    .collect(Collectors.toSet());
        }
        return new ArrayList<>(uniqueResault);
    }


    @Transactional(readOnly = true)
    public Long getCommentCountForPost(Integer post_id) {
        Optional<Post> postOptional = postRepository.findById(post_id);

        if (postOptional.isPresent()) {
            Post post = postOptional.get();

            return (long) post.getComments().size();
        }

        return 0L;
    }

    public List<Report> getAllReportsExcludingAccepted() {
        return reportRepository.findByAccepted(false);
    }

    public List<Post> findAllExcludingPostsWithAcceptedReports() {
        return postRepository.findAllExcludingPostsWithAcceptedReports();
    }

}
