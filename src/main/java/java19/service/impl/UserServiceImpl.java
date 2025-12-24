package java19.service.impl;

import jakarta.transaction.Transactional;
import java19.configuration.jwt.JwtService;
import java19.dto.SimpleResponse;
import java19.dto.postDto.response.PostResponse;
import java19.dto.userDto.request.UserUpdateRequest;
import java19.dto.userDto.response.UserProfileResponse;
import java19.dto.userDto.response.UserResponse;
import java19.dto.userDto.response.UserResponseById;
import java19.dto.userDto.response.UserUpdateResponse;
import java19.entity.Follower;
import java19.entity.Image;
import java19.entity.User;
import java19.excepion.AccessIsDeniedException;
import java19.excepion.NoSuchElementException;
import java19.repository.FollowerRepository;
import java19.repository.UserRepository;
import java19.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final FollowerRepository followerRepository;

    @Override
    public UserResponseById getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(()
                -> new NoSuchElementException("User with such + " + id + " not found"));
        return UserResponseById
                .builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @Override
    public UserUpdateResponse updateUser(Long id, UserUpdateRequest userUpdateRequest) {
        User currentUser = jwtService.checkAuthentication();
        User oldUser = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User with such + " + id + " not found"));
        if (!currentUser.getEmail().equals(oldUser.getEmail())) {
            throw new AccessIsDeniedException("You can't update this user");
        }
        oldUser.setUserName(userUpdateRequest.userName());
        oldUser.setEmail(userUpdateRequest.email());
        oldUser.setPassword(passwordEncoder.encode(userUpdateRequest.password()));
        oldUser.setPhoneNumber(userUpdateRequest.phoneNumber());
        userRepository.save(oldUser);
        if (oldUser.getFollower() == null) {
            Follower follower = new Follower();
            follower.setUser(oldUser);
            followerRepository.save(follower);
        }
        String token = jwtService.generateToken(oldUser);
        return UserUpdateResponse
                .builder()
                .status(HttpStatus.OK)
                .message("user is updated")
                .token(token)
                .build();
    }

    @Override
    public SimpleResponse deleteUserById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else throw new NoSuchElementException("User with such + " + id + " not found");
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message(String.format("User with %s id is deleted", id))
                .build();
    }

    @Override
    public UserProfileResponse userProfile(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User with such + " + id + " not found"));
        int countSubscriptions = user.getFollower() == null ? 0 : user.getFollower().getSubscriptions().size();
        int countSubscribers = user.getFollower() == null ? 0 : user.getFollower().getSubscriptions().size();
        List<PostResponse> postList = user.getPosts()
                .stream().map(p -> PostResponse
                        .builder()
                        .id(p.getId())
                        .title(p.getTitle())
                        .description(p.getDescription())
                        .imageUrl(String.valueOf(p.getImages().stream().map(Image::getImageUrl)
                                .toList()))
                        .collabUsers(p.getCollabs().stream().map(User::getUsername)
                                .toList())
                        .createdAt(p.getCreatedAt())
                        .build())
                .sorted(Comparator.comparing(PostResponse::createdAt)
                        .reversed())
                .toList();
        String fullName = user.getUserInfo() != null && user.getUserInfo().getFullName() != null ? user.getUserInfo().getFullName() : "";
        String imageUrl = user.getUserInfo() != null && user.getUserInfo().getImage() != null ? user.getUserInfo().getImage() : null;
        return UserProfileResponse.builder().username(user.getUsername()).fullName(fullName)
                .image(imageUrl).subscribers(countSubscribers).subscriptions(countSubscriptions).posts(postList).build();
    }
}
