package java19.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "post_gen")
    @SequenceGenerator(name = "post_gen", sequenceName = "post_seq", allocationSize = 1)

    Long id;
    String title;
    String description;
    LocalDate createdAt;

    @ManyToOne(cascade = {
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH})
    User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = {
            CascadeType.REMOVE,
            CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE
    })
    List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = {
            CascadeType.PERSIST,
            CascadeType.REMOVE,
            CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE
    })
    List<Comment> comments = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "post_collabs",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "collab_id"))
    List<User> collabs = new ArrayList<>();

}
