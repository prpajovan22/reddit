package ftn.com.reddit.models;

import javax.persistence.*;

@Entity
@Table(name = "rule")
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rule_id", unique = true, nullable = false)
    private Integer rule_id;

    @Column(name = "description", unique = false, nullable = false)
    private String description;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "community_id", referencedColumnName = "community_id")
    private Community community;

    public Rule(Integer rule_id, String description, Community community) {
        this.rule_id = rule_id;
        this.description = description;
        this.community = community;
    }

    public Integer getRule_id() {
        return rule_id;
    }

    public void setRule_id(Integer rule_id) {
        this.rule_id = rule_id;
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
