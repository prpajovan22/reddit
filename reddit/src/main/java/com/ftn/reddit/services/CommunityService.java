package com.ftn.reddit.services;

import com.ftn.reddit.DTO.CommunityDTO;
import com.ftn.reddit.Interface.CommunityInterface;
import com.ftn.reddit.model.Community;
import com.ftn.reddit.model.Post;
import com.ftn.reddit.model.ReactionType;
import com.ftn.reddit.model.pretraga.CommunitySearchCriteria;
import com.ftn.reddit.repositorys.CommunityRepository;
import com.ftn.reddit.repositorys.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommunityService implements CommunityInterface {

    @Autowired
    private CommunityRepository communityRepository;
    @Autowired
    private PostRepository postRepository;

    @Override
    public List<Community> findAll() {
        return communityRepository.findAll();
    }

    @Override
    public Community save(Community post) {
        return communityRepository.save(post);
    }

    @Override
    public Community findById(Integer id) {
        return communityRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Community post) {
        communityRepository.delete(post);
    }

    public List<Community> searchCommunities(CommunitySearchCriteria searchCriteria) {
        Set<Community> combinedSearchResult = new HashSet<>();
        boolean shouldSearchMore = true;
        if (StringUtils.isEmpty(searchCriteria.getName()) && StringUtils.isEmpty(searchCriteria.getDescription())
                && StringUtils.isEmpty(searchCriteria.getCommunityPDFName())) {
            combinedSearchResult.addAll(communityRepository.findAll());
            shouldSearchMore = false;
        }


        if (!StringUtils.isEmpty(searchCriteria.getName()) && shouldSearchMore) {
            List<Community> nameSearchResult = communityRepository.findByNameContainingIgnoreCase(searchCriteria.getName().toLowerCase());
            combinedSearchResult.addAll(nameSearchResult);
        }

        if (!StringUtils.isEmpty(searchCriteria.getDescription()) && shouldSearchMore) {
            List<Community> descriptionSearchResult = communityRepository.findByDescriptionContainingIgnoreCase(searchCriteria.getDescription().toLowerCase());
            combinedSearchResult.addAll(descriptionSearchResult);
        }

        if (!StringUtils.isEmpty(searchCriteria.getCommunityPDFName()) && shouldSearchMore) {
            List<Community> descriptionSearchResult = communityRepository.findByCommunityPDFNameContainingIgnoreCase(searchCriteria.getCommunityPDFName().toLowerCase());
            combinedSearchResult.addAll(descriptionSearchResult);
        }

        Set<Community> uniqueResault = new HashSet<>(combinedSearchResult);

        if(searchCriteria.getFromPostCount() > 0 || searchCriteria.getToPostCount() > 0){
            uniqueResault = uniqueResault.stream().filter(post -> {
                return post.getPosts().size() >= searchCriteria.getFromPostCount() && post.getPosts().size() <= searchCriteria.getToPostCount();
            }).collect(Collectors.toSet());
        }

        return new ArrayList<>(uniqueResault);
    }

    public Long getPostCountForCommunity(Integer communityId) {
        Optional<Community> communityOptional = communityRepository.findById(communityId);

        if (communityOptional.isPresent()) {
            Community community = communityOptional.get();
            return (long) community.getPosts().size();
        }
        return 0L;
    }

    public boolean isSuspended(Integer communityId) {
        Optional<Community> communityOptional = communityRepository.findById(communityId);

        if (communityOptional.isPresent()) {
            Community community = communityOptional.get();
            return community.isSuspended();
        }

        return false;
    }

    @Transactional
    public Double getAverageReactionsPerPost(Integer communityId) {
        Optional<Community> communityOptional = communityRepository.findById(communityId);

        if (communityOptional.isPresent()) {
            Community community = communityOptional.get();
            Set<Post> posts = community.getPosts();

            long totalReactions = posts.stream()
                    .flatMap(post -> post.getReactions().stream())
                    .filter(reaction -> reaction.getType() == ReactionType.UPWOTE || reaction.getType() == ReactionType.DOWNWOTE)
                    .count();

            double averageReactionsPerPost = (double) totalReactions / posts.size();
            return averageReactionsPerPost;
        }

        return 0.0;
    }

    public CommunityDTO getCommunityByPostId(Integer postId) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            Community community = post.getCommunity();

            CommunityDTO communityDTO = new CommunityDTO();
            communityDTO.setCommunity_id(community.getCommunity_id());
            communityDTO.setName(community.getName());
            communityDTO.setDescription(community.getDescription());

            return communityDTO;
        }
        return null;
    }

}
