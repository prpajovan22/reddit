package ftn.com.reddit.models;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "admins")
public class Admin  extends Users implements Serializable {
    public Admin(Integer id, String username, String password, String email, String avatar, LocalDate registrationDate, String description, String displayName, Set<Banned> banneds) {
        super(id, username, password, email, avatar, registrationDate, description, displayName, banneds);
    }
}
