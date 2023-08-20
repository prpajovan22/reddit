package com.ftn.reddit.DTO;

import com.ftn.reddit.model.Reaction;
import com.ftn.reddit.model.ReactionType;

import java.time.LocalDate;

public class ReactionDTO {

    private Integer reaction_id;
    private LocalDate timestamp;
    private ReactionType type;
    private UsersDTO user;

    public ReactionDTO(Integer reaction_id, LocalDate timestamp, ReactionType type, UsersDTO user) {
        this.reaction_id = reaction_id;
        this.timestamp = timestamp;
        this.type = type;
        this.user = user;
    }

    public ReactionDTO(Reaction reaction){
        this.reaction_id = reaction.getReaction_id();
        this.timestamp = reaction.getTimestamp();
        this.type = reaction.getType();
        this.user = new UsersDTO(reaction.getUser());
    }

    public Integer getReaction_id() {
        return reaction_id;
    }

    public void setReaction_id(Integer reaction_id) {
        this.reaction_id = reaction_id;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }

    public ReactionType getType() {
        return type;
    }

    public void setType(ReactionType type) {
        this.type = type;
    }

    public UsersDTO getUser() {
        return user;
    }

    public void setUser(UsersDTO user) {
        this.user = user;
    }
}
