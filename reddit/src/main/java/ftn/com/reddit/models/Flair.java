package ftn.com.reddit.models;


import javax.persistence.*;
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

    @OneToMany(mappedBy = "flair", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Post> post;

    public Flair(Integer flair_id, String name, Set<Post> post) {
        this.flair_id = flair_id;
        this.name = name;
        this.post = post;
    }

    public Flair() {
        super();
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

    public Set<Post> getPost() {
        return post;
    }

    public void setPost(Set<Post> post) {
        this.post = post;
    }
}
