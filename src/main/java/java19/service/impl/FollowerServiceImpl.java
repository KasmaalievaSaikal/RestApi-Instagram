package java19.service.impl;

import jakarta.transaction.Transactional;
import java19.configuration.jwt.JwtService;
import java19.dto.followerDto.response.FollowerUserResponse;
import java19.dto.followerDto.response.ProfileSubscribeResponse;
import java19.entity.Follower;
import java19.entity.User;
import java19.excepion.AccessIsDeniedException;
import java19.excepion.NotFoundException;
import java19.repository.FollowerRepository;
import java19.repository.UserRepository;
import java19.service.FollowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowerServiceImpl implements FollowerService {

    private final FollowerRepository followerRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Override
    public List<FollowerUserResponse> search(String name) {
        List<User> users = userRepository.searchUserByUsernameOrByFullName(name);
        return users.stream().map(u -> FollowerUserResponse
                        .builder()
                        .userName(u.getUsername())
                        .image(u.getUserInfo() == null || u.getUserInfo()
                                .getImage() == null ? null : u.getUserInfo().getImage()).
                        build())
                .toList();
    }

    @Override
    public ProfileSubscribeResponse subscribe(Long targetId) {
        System.out.println("test");
        User currentUser = jwtService.checkAuthentication();
        Long userId = currentUser.getId();
        if (userId.equals(targetId)) {
            throw new AccessIsDeniedException();
        }

        Follower follower = followerRepository.findByUser_Id(userId)
                .orElseGet(() -> followerRepository.save(new Follower(null, currentUser, new ArrayList<>(), new ArrayList<>())));
        User userTarget = userRepository.findById(targetId).orElseThrow(() ->
                new NotFoundException("target user not found"));
        List<User> subscriptions = follower.getSubscriptions();
        boolean exists = subscriptions.stream().anyMatch(sub -> sub.getId().equals(targetId));
        if (!exists) {
            subscriptions.add(userTarget);
            if (userTarget.getFollower() == null) {
                Follower targetFollower = new Follower();
                targetFollower.setUser(userTarget);
                userTarget.setFollower(targetFollower);
            }
            userTarget.getFollower().getSubscribers().add(currentUser);
            currentUser.setFollowingCount(follower.getSubscriptions().size());
            userTarget.setFollowersCount(userTarget.getFollower().getSubscribers().size());

            followerRepository.save(follower);
            userRepository.save(currentUser);
            userRepository.save(userTarget);
            return ProfileSubscribeResponse
                    .builder()
                    .targetUserId(targetId)
                    .subscribed(true)
                    .button("Отменить подписку")
                    .build();
        } else {
            subscriptions.removeIf(u -> u.getId().equals(targetId));
            if (userTarget.getFollower() != null) {
                userTarget.getFollower().getSubscribers().removeIf(u -> u.getId().equals(userId));
            }
            currentUser.setFollowingCount(follower.getSubscriptions().size());
            userTarget.setFollowersCount(userTarget.getFollower() != null ? userTarget.getFollower().getSubscribers().size() : 0);

            followerRepository.save(follower);
            userRepository.save(currentUser);
            userRepository.save(userTarget);
            return ProfileSubscribeResponse
                    .builder()
                    .targetUserId(targetId)
                    .subscribed(false)
                    .button("Подписаться")
                    .build();
        }
    }

    @Override
    public List<FollowerUserResponse> getAllSubscribedByUserId(Long userId) {
        User currentUser = jwtService.checkAuthentication();
        return followerRepository.findSubscribers(userId);
    }


    @Override
    public List<FollowerUserResponse> getAllSubscriptionsByUserId(Long userId) {
        User currentUser = jwtService.checkAuthentication();
        return followerRepository.findSubscriptions(userId);
    }
}
