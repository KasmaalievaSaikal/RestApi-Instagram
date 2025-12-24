package java19.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java19.enums.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    Long id;
    @Column(name = "user_name", unique = true)
    String userName;
    @Column(unique = true, nullable = false)
    String password;
    @Column(unique = true, nullable = false)
    String email;
    @Column(name = "phone_number", nullable = false)
    String phoneNumber;
    @Enumerated(EnumType.STRING)
    Role role;

    int followersCount;

    int followingCount;
    @OneToMany(mappedBy = "user", cascade = {
            CascadeType.REMOVE,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH})
    List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = {
            CascadeType.REMOVE,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH
    })
    List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = {
            CascadeType.PERSIST, CascadeType.DETACH,
            CascadeType.MERGE, CascadeType.REFRESH,
            CascadeType.REMOVE}, orphanRemoval = true)
    @JsonManagedReference
    List<Like> likes = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = {
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH,
            CascadeType.REMOVE})
    UserInfo userInfo;

    @OneToOne(mappedBy = "user", cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH,
            CascadeType.REFRESH,
            CascadeType.REMOVE}, orphanRemoval = true)
    @JsonManagedReference
    Follower follower;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }


}
