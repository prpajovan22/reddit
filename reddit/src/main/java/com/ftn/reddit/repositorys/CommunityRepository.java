package com.ftn.reddit.repositorys;

import com.ftn.reddit.model.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Integer> {

    List<Community> findByNameContainingIgnoreCaseAndDescriptionContainingIgnoreCase(
            String name,String description
    );
}
