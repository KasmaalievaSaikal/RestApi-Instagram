package java19.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "followers")
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Follower {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "follower_gen")
    @SequenceGenerator(name = "follower_gen", sequenceName = "follower_seq", allocationSize = 1)
    Long id;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    User user;

    @ManyToMany
    @JoinTable(name = "subscribers", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "subscriber_id"))
    List<User> subscribers = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "subscription",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "subscription_id")
    )
    List<User> subscriptions = new ArrayList<>();

}
