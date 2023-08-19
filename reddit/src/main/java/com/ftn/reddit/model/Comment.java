package com.ftn.reddit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ftn.reddit.model.Users;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id", unique = true, nullable = false)
    private Integer comment_id;

    @Column(name = "text", unique = false, nullable = false)
    private String text;

    @Column(name = "timestamp", unique = false, nullable = false)
    private LocalDate timestamp;

    @Column(name = "isDeleted", unique = false, nullable = false)
    private boolean isDeleted;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_comment_id", referencedColumnName = "comment_id")
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Comment> replies;
    @JsonIgnore
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Reaction> reactions;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Report> reports;

    @ManyToOne(fetch =FetchType.EAGER)
    @JoinColumn(name="post_id", referencedColumnName = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    private Users user;


    public Comment() {

    }

    public Comment(Integer comment_id, String text, LocalDate timestamp, boolean isDeleted, Comment parentComment, Set<Comment> replies, Set<Reaction> reactions, Set<Report> reports, Post post, Users user) {
        this.comment_id = comment_id;
        this.text = text;
        this.timestamp = timestamp;
        this.isDeleted = isDeleted;
        this.parentComment = parentComment;
        this.replies = replies;
        this.reactions = reactions;
        this.reports = reports;
        this.post = post;
        this.user = user;
    }

    public Integer getComment_id() {
        return comment_id;
    }

    public void setComment_id(Integer comment_id) {
        this.comment_id = comment_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public Set<Comment> getReplies() {
        return replies;
    }

    public void setReplies(Set<Comment> replies) {
        this.replies = replies;
    }

    public Set<Reaction> getReactions() {
        return reactions;
    }

    public void setReactions(Set<Reaction> reactions) {
        this.reactions = reactions;
    }

    public Set<Report> getReports() {
        return reports;
    }

    public void setReports(Set<Report> reports) {
        this.reports = reports;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
