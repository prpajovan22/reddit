package com.ftn.reddit.services;

import com.ftn.reddit.Interface.ReactionInterface;
import com.ftn.reddit.model.Reaction;
import com.ftn.reddit.repositorys.ReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReactionService implements ReactionInterface {

    @Autowired
    private ReactionRepository reactionRepository;

    @Override
    public Reaction save(Reaction reaction) {
        return reactionRepository.save(reaction);
    }
}
