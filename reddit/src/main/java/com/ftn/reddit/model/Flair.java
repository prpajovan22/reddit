package com.ftn.reddit.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "flair")
public class Flair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flair_id", unique = true, nullable = false)
    private Integer flair_id;

    @Column(name = "name", unique = false, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    private Post post;

    @JsonIgnore
    @OneToMany(mappedBy = "flair")
    private Set<Community> communities;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private Users user;

    public Flair() {

    }

    public Flair(Integer flair_id, String name,Post post, Set<Community> communities, Users user) {
        this.flair_id = flair_id;
        this.name = name;
        this.post = post;
        this.communities = communities;
        this.user = user;
    }

    public Integer getFlair_id() {
        return flair_id;
    }

    public void setFlair_id(Integer flair_id) {
        this.flair_id = flair_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Post getPosts() {
        return post;
    }

    public void setPosts(Post post) {
        this.post = post;
    }

    public Set<Community> getCommunities() {
        return communities;
    }

    public void setCommunities(Set<Community> communities) {
        this.communities = communities;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
