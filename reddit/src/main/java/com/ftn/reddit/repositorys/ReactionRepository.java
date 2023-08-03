package com.ftn.reddit.repositorys;

import com.ftn.reddit.model.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction,Integer> {
}
