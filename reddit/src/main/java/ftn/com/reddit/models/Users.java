package ftn.com.reddit.models;

import jakarta.persistence.*;
import lombok.Data;

import javax.management.relation.Role;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", unique = false, nullable = false)
    private String password;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "avatar", unique = false, nullable = false)
    private String avatar;

    @Column(name = "registrationDate", unique = false, nullable = false)
    private LocalDate registrationDate;

    @Column(name = "description", unique = false, nullable = false)
    private String description;

    @Column(name = "displayName", unique = false, nullable = false)
    private String displayName;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Banned> banneds;

    public Users(Integer id, String username, String password, String email, String avatar, LocalDate registrationDate, String description, String displayName, Set<Banned> banneds) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.avatar = avatar;
        this.registrationDate = registrationDate;
        this.description = description;
        this.displayName = displayName;
        this.banneds = banneds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Set<Banned> getBanneds() {
        return banneds;
    }

    public void setBanneds(Set<Banned> banneds) {
        this.banneds = banneds;
    }
}
