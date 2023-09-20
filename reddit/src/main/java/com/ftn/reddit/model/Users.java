package com.ftn.reddit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ftn.reddit.DTO.UsersDTO;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "users")
public class Users implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true, nullable = false)
    private Integer user_id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", unique = false, nullable = false)
    private String password;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "avatar", unique = false)
    private String avatar;

    @Column(name = "registrationDate", unique = false, nullable = false)
    private LocalDate registrationDate;

    @Column(name = "description", unique = false, nullable = true)
    private String description;

    @Column(name = "displayName", unique = false, nullable = true)
    private String displayName;

    @Column(name = "suspended", unique = false, nullable = true)
    private boolean isSuspended;

    @Column(name = "userRole", unique = false, nullable = false)
    private UserRole userRole;


    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Post> post;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Reaction> reactions;
    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Banned> banned;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Flair> flairs;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Comment> comments;

    @JsonIgnore
    @OneToMany(mappedBy = "byUser", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Report> reports;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "community_id", referencedColumnName = "community_id")
    private Community community;

    public Users() {
    }


    public Users(Integer user_id, String username, String password, String email, String avatar, LocalDate registrationDate, String description, String displayName, boolean isSuspended, UserRole userRole, Set<Post> post, Set<Reaction> reactions, Set<Banned> banned, Set<Flair> flairs, Set<Comment> comments, Set<Report> reports, Community community) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.avatar = avatar;
        this.registrationDate = registrationDate;
        this.description = description;
        this.displayName = displayName;
        this.isSuspended = isSuspended;
        this.userRole = userRole;
        this.post = post;
        this.reactions = reactions;
        this.banned = banned;
        this.flairs = flairs;
        this.comments = comments;
        this.reports = reports;
        this.community = community;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Set<Post> getPost() {
        return post;
    }

    public void setPost(Set<Post> post) {
        this.post = post;
    }

    public Set<Banned> getBanned() {
        return banned;
    }

    public void setBanned(Set<Banned> banned) {
        this.banned = banned;
    }

    public Set<Flair> getFlairs() {
        return flairs;
    }

    public void setFlairs(Set<Flair> flairs) {
        this.flairs = flairs;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Set<Report> getReports() {
        return reports;
    }

    public void setReports(Set<Report> reports) {
        this.reports = reports;
    }

    public Set<Reaction> getReactions() {
        return reactions;
    }

    public void setReactions(Set<Reaction> reactions) {
        this.reactions = reactions;
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    public void setSuspended(boolean suspended) {
        isSuspended = suspended;
    }
}
