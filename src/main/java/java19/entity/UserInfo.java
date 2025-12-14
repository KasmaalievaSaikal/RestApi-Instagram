package java19.entity;


import jakarta.persistence.*;
import java19.enums.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "usersInfos")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userInfo_gen")
    @SequenceGenerator(name = "userInfo_gen", sequenceName = "userInfo_seq", allocationSize = 1)

    Long id;
    String fullName;
    String biography;
    Gender gender;
    String image;
}
