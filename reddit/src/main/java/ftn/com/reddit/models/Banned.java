package ftn.com.reddit.models;

import lombok.Data;
import org.apache.catalina.User;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "banned")
public class Banned {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "banned_id", unique = true, nullable = false)
    private Integer banned_id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "bannedBy", referencedColumnName = "user_id")
    private Moderator bannedBy;

    @Column(name = "timestamp", unique = false, nullable = false)
    private LocalDate timestamp;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "community_id", referencedColumnName = "community_id")
    private Community community;

    public Banned(Integer banned_id, Moderator bannedBy, LocalDate timestamp, User user, Community community) {
        this.banned_id = banned_id;
        this.bannedBy = bannedBy;
        this.timestamp = timestamp;
        this.user = user;
        this.community = community;
    }

    public Integer getBanned_id() {
        return banned_id;
    }

    public void setBanned_id(Integer banned_id) {
        this.banned_id = banned_id;
    }

    public Moderator getBannedBy() {
        return bannedBy;
    }

    public void setBannedBy(Moderator bannedBy) {
        this.bannedBy = bannedBy;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }
}
