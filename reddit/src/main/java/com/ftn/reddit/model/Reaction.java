package com.ftn.reddit.model;

import jakarta.persistence.*;
import org.apache.catalina.User;

import java.time.LocalDate;

@Entity
@Table(name = "reaction")
public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reaction_id", unique = true, nullable = false)
    private Integer reaction_id;

    @Column(name = "timestamp", unique = false, nullable = false)
    private LocalDate timestamp;

    @Column(name = "type", unique = false, nullable = false)
    private ReactionType type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "comment_id", referencedColumnName = "comment_id")
    private Comment comment;

    @ManyToOne(fetch =FetchType.EAGER)
    @JoinColumn(name="post_id", referencedColumnName = "post_id")
    private Post post;

    public Reaction(Integer reaction_id, LocalDate timestamp, ReactionType type, Users user, Comment comment, Post post) {
        this.reaction_id = reaction_id;
        this.timestamp = timestamp;
        this.type = type;
        this.user = user;
        this.comment = comment;
        this.post = post;
    }

    public Reaction() {

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


    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
