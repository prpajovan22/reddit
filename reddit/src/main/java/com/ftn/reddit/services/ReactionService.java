package com.ftn.reddit.services;

import com.ftn.reddit.Interface.ReactionInterface;
import com.ftn.reddit.model.*;
import com.ftn.reddit.repositorys.CommentRepository;
import com.ftn.reddit.repositorys.PostRepository;
import com.ftn.reddit.repositorys.ReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReactionService implements ReactionInterface {

    @Autowired
    private ReactionRepository reactionRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

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

    public void upvoteComment(Integer comment_id, Users user) {
        Reaction existingReaction = reactionRepository.findByUserAndComment(user, commentRepository.findById(comment_id).orElse(null));
        if (existingReaction != null && existingReaction.getType() == ReactionType.UPWOTE) {
            return;
        }

        if (existingReaction != null && existingReaction.getType() == ReactionType.DOWNWOTE) {
            reactionRepository.delete(existingReaction);
        }

        Reaction reaction = new Reaction();
        reaction.setTimestamp(LocalDate.from(LocalDateTime.now()));
        reaction.setType(ReactionType.UPWOTE);
        reaction.setComment(commentRepository.findById(comment_id).orElse(null));
        reaction.setUser(user);

        reactionRepository.save(reaction);
    }

    public void downvoteComment(Integer comment_id, Users user) {
        Reaction existingReaction = reactionRepository.findByUserAndComment(user, commentRepository.findById(comment_id).orElse(null));
        if (existingReaction != null && existingReaction.getType() == ReactionType.DOWNWOTE) {
            return;
        }

        if (existingReaction != null && existingReaction.getType() == ReactionType.UPWOTE) {
            reactionRepository.delete(existingReaction);
        }

        Reaction reaction = new Reaction();
        reaction.setTimestamp(LocalDate.from(LocalDateTime.now()));
        reaction.setType(ReactionType.DOWNWOTE);
        reaction.setComment(commentRepository.findById(comment_id).orElse(null));
        reaction.setUser(user);

        reactionRepository.save(reaction);
    }

    public boolean hasUserDownvotedPost(Users user, Post post) {
        return reactionRepository.existsByUserAndPostAndType(user, post, ReactionType.DOWNWOTE);
    }

    public boolean hasUserUpvotedPost(Users user, Post post) {
        return reactionRepository.existsByUserAndPostAndType(user, post, ReactionType.UPWOTE);
    }


    public boolean hasUserDownvotedComment(Users user, Comment comment) {
        return reactionRepository.existsByUserAndCommentAndType(user, comment, ReactionType.DOWNWOTE);
    }

    public boolean hasUserUpvotedComment(Users user, Comment comment) {
        return reactionRepository.existsByUserAndCommentAndType(user, comment, ReactionType.UPWOTE);
    }
     @Transactional
    public void removeUpvote(Post post, Users user) {
        reactionRepository.deleteByPostAndUserAndType(post, user, ReactionType.UPWOTE);
    }

    @Transactional
    public void removeDownvote(Post post, Users user) {
        reactionRepository.deleteByPostAndUserAndType(post, user, ReactionType.DOWNWOTE);
    }

    @Transactional
    public void removeUpvoteComment(Comment comment, Users user) {
        reactionRepository.deleteByCommentAndUserAndType(comment, user, ReactionType.UPWOTE);
    }

    @Transactional
    public void removeDownvoteComment(Comment comment, Users user) {
        reactionRepository.deleteByCommentAndUserAndType(comment, user, ReactionType.DOWNWOTE);
    }
}
