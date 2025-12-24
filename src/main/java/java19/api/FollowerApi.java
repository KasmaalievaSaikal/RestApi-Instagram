package java19.api;

import java19.dto.followerDto.response.FollowerUserResponse;
import java19.dto.followerDto.response.ProfileSubscribeResponse;
import java19.service.FollowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/followers")
@RequiredArgsConstructor
public class FollowerApi {

    private final FollowerService followerService;

    @GetMapping("/search-user")
    public List<FollowerUserResponse> searchByUsernameOrFullName(@RequestParam String someName) {
        return followerService.search(someName);
    }

    @PostMapping("/subscribe")
    public ProfileSubscribeResponse subscribe(@RequestParam Long targetId) {
        return followerService.subscribe(targetId);
    }

    @GetMapping("/{userId}/find-subscribers")
    public List<FollowerUserResponse> getSubscribers(@PathVariable Long userId) {
        return followerService.getAllSubscribedByUserId(userId);
    }

    @GetMapping("/{userId}/get-subscriptions")
    public List<FollowerUserResponse> getSubscriptions(@PathVariable Long userId) {
        return followerService.getAllSubscriptionsByUserId(userId);
    }
}
