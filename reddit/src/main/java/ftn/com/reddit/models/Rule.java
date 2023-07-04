package ftn.com.reddit.models;

import javax.persistence.*;

@Entity
@Table(name = "rule")
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rule_id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "description", unique = false, nullable = false)
    private String description;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "community_id", referencedColumnName = "community_id")
    private Community community;

    public Rule(Integer id, String description, Community community) {
        this.id = id;
        this.description = description;
        this.community = community;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Community getCommunity() {
        return community;
    }

    public void setCommunity(Community community) {
        this.community = community;
    }
}
