package java19.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comments")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_gen")
    @SequenceGenerator(name = "comment_gen", sequenceName = "comment_seq", allocationSize = 1)

    Long id;
    String comment;
    LocalDate createdAt;

    @ManyToOne
    User user;

    @ManyToOne
    Post post;

    @OneToMany(mappedBy = "comment", cascade = {
            CascadeType.REMOVE,
            CascadeType.REFRESH,
            CascadeType.DETACH,
            CascadeType.MERGE
    })
    List<Like> likes = new ArrayList<>();
}
