package ftn.com.reddit.models;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "community")
public class Community {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_id", unique = true, nullable = false)
    private Integer community_id;

    @Column(name = "name", unique = false, nullable = false)
    private String name;

    @Column(name = "description", unique = false, nullable = true)
    private String description;

    @Column(name = "creationDate", unique = false, nullable = true)
    private String creationDate;

    @Column(name = "isSuspended", unique = false, nullable = true)
    private boolean isSuspended;

    @Column(name = "suspendedReason", unique = false, nullable = true)
    private String suspendedReason;

    @Column(name = "communityPDFPath", unique = false, nullable = true)
    private String communityPDFPath;

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Post> post;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinTable(name = "flair_community", joinColumns = {@JoinColumn(name = "flair_id")},
            inverseJoinColumns = {@JoinColumn(name = "id")})
    private Set<Flair> flair = new HashSet<>();

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Rule> rule;

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Banned> banned;

    public Community(Integer community_id, String name,
                     String description, String creationDate, boolean isSuspended, String suspendedReason,
                     String communityPDFPath, Set<Post> post, Set<Flair> flair, Set<Rule> rule, Set<Banned> banned) {
        this.community_id = community_id;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.isSuspended = isSuspended;
        this.suspendedReason = suspendedReason;
        this.communityPDFPath = communityPDFPath;
        this.post = post;
        this.flair = flair;
        this.rule = rule;
        this.banned = banned;
    }

    public Community() {

    }

    public Integer getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(Integer community_id) {
        this.community_id = community_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    public void setSuspended(boolean suspended) {
        isSuspended = suspended;
    }

    public String getSuspendedReason() {
        return suspendedReason;
    }

    public void setSuspendedReason(String suspendedReason) {
        this.suspendedReason = suspendedReason;
    }

    public String getCommunityPDFPath() {
        return communityPDFPath;
    }

    public void setCommunityPDFPath(String communityPDFPath) {
        this.communityPDFPath = communityPDFPath;
    }

    public Set<Post> getPost() {
        return post;
    }

    public void setPost(Set<Post> post) {
        this.post = post;
    }

    public Set<Flair> getFlair() {
        return flair;
    }

    public void setFlair(Set<Flair> flair) {
        this.flair = flair;
    }

    public Set<Rule> getRule() {
        return rule;
    }

    public void setRule(Set<Rule> rule) {
        this.rule = rule;
    }

    public Set<Banned> getBanned() {
        return banned;
    }

    public void setBanned(Set<Banned> banned) {
        this.banned = banned;
    }
}
