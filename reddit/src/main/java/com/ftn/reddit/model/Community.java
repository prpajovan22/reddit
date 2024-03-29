package com.ftn.reddit.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

//@Document(indexName = "community")
//@Setting(settingPath = "/analyzers/customAnalyzer.json")
@Entity
@Table(name = "community")
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_id", unique = true, nullable = false)
    private Integer community_id;

    @Field(type = FieldType.Text, analyzer = "serbian_analyzer")
    @Column(name = "name", unique = false, nullable = false)
    private String name;
    @Field(type = FieldType.Text, analyzer = "serbian_analyzer")
    @Column(name = "description", unique = false, nullable = true)
    private String description;

    @Column(name = "creationDate", unique = false, nullable = true)
    private LocalDate creationDate;

    @Column(name = "isSuspended", unique = false, nullable = true)
    private boolean isSuspended;

    @Column(name = "suspendedReason", unique = false, nullable = true)
    private String suspendedReason;

    @Column(name = "communityPDFPath", unique = false, nullable = true)
    private String communityPDFPath;

    @Field(type = FieldType.Text, analyzer = "serbian_analyzer")
    @Column(name = "communityPDFName", unique = false, nullable = true)
    private String communityPDFName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rule_id", referencedColumnName = "rule_id")
    private Rule rule;

    /*@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "banned_id", referencedColumnName = "banned_id")
    private Banned banned;*/

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Users> moderators = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Post> posts;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "flair_id", nullable = true)
    private Flair flair;

    public Community() {

    }

    public Community(Integer community_id, String name, String description, LocalDate creationDate, boolean isSuspended, String suspendedReason, String communityPDFPath, String communityPDFName, Rule rule, Set<Users> moderators, Set<Post> posts, Flair flair) {
        this.community_id = community_id;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.isSuspended = isSuspended;
        this.suspendedReason = suspendedReason;
        this.communityPDFPath = communityPDFPath;
        this.communityPDFName = communityPDFName;
        this.rule = rule;
        this.moderators = moderators;
        this.posts = posts;
        this.flair = flair;
    }

    public Integer getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(Integer community_id) {
        this.community_id = community_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    public void setSuspended(boolean suspended) {
        isSuspended = suspended;
    }

    public String getSuspendedReason() {
        return suspendedReason;
    }

    public void setSuspendedReason(String suspendedReason) {
        this.suspendedReason = suspendedReason;
    }

    public String getCommunityPDFPath() {
        return communityPDFPath;
    }

    public void setCommunityPDFPath(String communityPDFPath) {
        this.communityPDFPath = communityPDFPath;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public Set<Users> getModerators() {
        return moderators;
    }

    public void setModerators(Set<Users> moderators) {
        this.moderators = moderators;
    }

    public Set<Post> getPosts() {
        return posts;
    }

    public void setPosts(Set<Post> posts) {
        this.posts = posts;
    }

    public Flair getFlair() {
        return flair;
    }

    public void setFlair(Flair flair) {
        this.flair = flair;
    }

    public String getCommunityPDFName() {
        return communityPDFName;
    }

    public void setCommunityPDFName(String communityPDFName) {
        this.communityPDFName = communityPDFName;
    }
}
