package com.ftn.reddit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class Moderator extends Users{

        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "community_id", referencedColumnName = "community_id")
        private Community community;
 //       @OneToOne
//        @MapsId
//        @JoinColumn(name = "user_id")
//        private Users user;
        public Moderator() {

        }

//        public Moderator(Set<Community> communities, Community community, Users user) {
//            this.community = community;
//            this.user = user;
//        }
//
//        public Moderator(Integer user_id, String username, String password, String email, String avatar, LocalDate registrationDate, String description, String displayName, Set<Post> post, Set<Banned> banned, Set<Flair> flairs, Set<Comment> comments, Moderator moderators, Community community, Users user) {
//            super(user_id, username, password, email, avatar, registrationDate, description, displayName, post, banned, flairs, comments, moderators);
//            this.community = community;
//            this.user = user;
//        }
//
//
//        public Users getUser() {
//            return user;
//        }
//
//        public void setUser(Users user) {
//            this.user = user;
//        }

        public Community getCommunity() {
            return community;
        }

        public void setCommunity(Community community) {
            this.community = community;
        }
}
