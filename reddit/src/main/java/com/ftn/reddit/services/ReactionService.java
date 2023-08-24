package com.ftn.reddit.services;

import com.ftn.reddit.Interface.ReactionInterface;
import com.ftn.reddit.model.Post;
import com.ftn.reddit.model.Reaction;
import com.ftn.reddit.model.ReactionType;
import com.ftn.reddit.model.Users;
import com.ftn.reddit.repositorys.PostRepository;
import com.ftn.reddit.repositorys.ReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReactionService implements ReactionInterface {

    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private PostRepository postRepository;

    @Override
    public Reaction save(Reaction reaction) {
        return reactionRepository.save(reaction);
    }

    public List<Reaction> findByPost(Post post) {
        return reactionRepository.findByPost(post);
    }

    public void upvotePost(Integer postId, Users user) {
        Reaction existingReaction = reactionRepository.findByUserAndPost(user, postRepository.findById(postId).orElse(null));
        if (existingReaction != null && existingReaction.getType() == ReactionType.UPWOTE) {
            return;
        }

        if (existingReaction != null && existingReaction.getType() == ReactionType.DOWNWOTE) {
            reactionRepository.delete(existingReaction);
        }

        Reaction reaction = new Reaction();
        reaction.setTimestamp(LocalDate.from(LocalDateTime.now()));
        reaction.setType(ReactionType.UPWOTE);
        reaction.setPost(postRepository.findById(postId).orElse(null));
        reaction.setUser(user);

        reactionRepository.save(reaction);
    }

    public void downvotePost(Integer postId, Users user) {
        Reaction existingReaction = reactionRepository.findByUserAndPost(user, postRepository.findById(postId).orElse(null));
        if (existingReaction != null && existingReaction.getType() == ReactionType.DOWNWOTE) {
            return;
        }

        if (existingReaction != null && existingReaction.getType() == ReactionType.UPWOTE) {
            reactionRepository.delete(existingReaction);
        }

        Reaction reaction = new Reaction();
        reaction.setTimestamp(LocalDate.from(LocalDateTime.now()));
        reaction.setType(ReactionType.DOWNWOTE);
        reaction.setPost(postRepository.findById(postId).orElse(null));
        reaction.setUser(user);

        reactionRepository.save(reaction);
    }

    public int getNetReactionForPost(Post post) {
        List<Reaction> reactions = reactionRepository.findByPost(post);

        int upvotes = 0;
        int downvotes = 0;

        for (Reaction reaction : reactions) {
            if (reaction.getType() == ReactionType.UPWOTE) {
                upvotes++;
            } else if (reaction.getType() == ReactionType.DOWNWOTE) {
                downvotes++;
            }
        }

        return upvotes - downvotes;
    }
}
