package com.ftn.reddit.repositorys;

import com.ftn.reddit.model.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Integer> {

    List<Community> findByNameContainingIgnoreCaseAndDescriptionContainingIgnoreCase(
            String name,String description
    );

    List<Community> findByNameContainingIgnoreCase(String name);

    List<Community> findByDescriptionContainingIgnoreCase(String description);

    List<Community> findByCommunityPDFNameContainingIgnoreCase(String communityPDFName);

    List<Community> findAll();

}
