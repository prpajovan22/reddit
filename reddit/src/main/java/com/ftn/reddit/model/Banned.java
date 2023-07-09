package com.ftn.reddit.model;

import org.apache.catalina.User;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "banned")
public class Banned {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "banned_id", unique = true, nullable = false)
    private Integer banned_id;

    @Column(name = "timestamp", unique = false, nullable = false)
    private LocalDate timestamp;
/*
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    private Users bannedBy;*/


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id", referencedColumnName = "user_id")
    private Users user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "community_id", referencedColumnName = "community_id")
    private Community community;

    public Banned() {

    }

    public Banned(Integer banned_id, LocalDate timestamp, Users bannedBy, Users user, Community community) {
        this.banned_id = banned_id;
        this.timestamp = timestamp;
        //this.bannedBy = bannedBy;
        this.user = user;
        this.community = community;
    }

    public Integer getBanned_id() {
        return banned_id;
    }

    public void setBanned_id(Integer banned_id) {
        this.banned_id = banned_id;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }

    public Users getUsers() {
        return user;
    }

    public void setUsers(Users users) {
        this.user = user;
    }


    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }

    /*public Users getBannedBy() {
        return bannedBy;
    }

    public void setBannedBy(Users bannedBy) {
        this.bannedBy = bannedBy;
    }*/

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
