package java19.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "users")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_gen")
    @SequenceGenerator(name = "user_gen", sequenceName = "user_seq", allocationSize = 1)

    Long id;
    @Column(name = "user_name", unique = true)
    String userName;
    @Column(unique = true, nullable = false)
    String password;
    @Email(message = "Email должен содержать символ @ и иметь корректный формат")
    @Column(unique = true, nullable = false)
    String email;
    @Pattern(regexp = "^//+[0-9]+$", message = "Номер телефона должен начинаться с '+' и содержать +996")
    @Column(name = "phone_number", nullable = false)
    String phoneNumber;

    @OneToMany(mappedBy = "user", cascade = {
            CascadeType.REMOVE,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH})
    List<Post> posts;

    @OneToOne(cascade = CascadeType.ALL)
    UserInfo userInfo;

    @OneToOne(cascade = CascadeType.ALL)
    Follower follower;

    @OneToOne(cascade = CascadeType.ALL)
    Image image;

    @OneToMany(mappedBy = "user", cascade = {
            CascadeType.REMOVE,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH
    })
    List<Comment> comments;

    @OneToOne(mappedBy = "user", cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH
    })
    Like like;

}
