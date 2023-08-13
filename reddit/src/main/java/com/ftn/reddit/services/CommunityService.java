package com.ftn.reddit.services;

import com.ftn.reddit.Interface.CommunityInterface;
import com.ftn.reddit.model.Community;
import com.ftn.reddit.model.pretraga.CommunitySearchCriteria;
import com.ftn.reddit.repositorys.CommunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommunityService implements CommunityInterface {

    @Autowired
    private CommunityRepository communityRepository;

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
        return communityRepository.findByNameContainingIgnoreCaseAndDescriptionContainingIgnoreCase(
                searchCriteria.getName(), searchCriteria.getDescription()
        );
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
            return community.isSuspended(); // Assuming you have a method to check if the community is suspended
        }

        // Community not found, consider it as not suspended
        return false;
    }
}
