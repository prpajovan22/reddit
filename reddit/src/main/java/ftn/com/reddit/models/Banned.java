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
    private Integer id;

    @Column(name = "timestamp", unique = false, nullable = false)
    private LocalDate timestamp;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "community_id", referencedColumnName = "community_id")
    private Community community;

    public Banned(Integer id, LocalDate timestamp, User user, Community community) {
        this.id = id;
        this.timestamp = timestamp;
        this.user = user;
        this.community = community;
    }

    public Banned() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
