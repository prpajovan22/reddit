package ftn.com.reddit.models;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "moderator")
public class Moderator extends Users implements Serializable {

    @OneToMany(mappedBy = "bannedBy", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Banned> banned;

    public Moderator(Integer id, String username, String password, String email, String avatar, LocalDate registrationDate, String description, String displayName, Set<Banned> banneds) {
        super(id, username, password, email, avatar, registrationDate, description, displayName, banneds);
    }

    public Set<Banned> getBanned() {
        return banned;
    }

    public void setBanned(Set<Banned> banned) {
        this.banned = banned;
    }
}
