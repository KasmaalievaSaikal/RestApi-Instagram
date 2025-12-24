package java19.api;


import java19.dto.SimpleResponse;
import java19.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/likes")
@RequiredArgsConstructor
public class  LikeApi {
    private final LikeService likeService;

    @PostMapping("/users/{userId}/posts/{postId}/post-like")
    public SimpleResponse addLikeToPost(@PathVariable Long userId, @PathVariable Long postId) {
        return likeService.likeForPost(userId, postId);
    }

    @PostMapping("/users/{userId}/comments/{commentId}/comment-like")
    public SimpleResponse addLikeToComment(@PathVariable("userId") Long userId, @PathVariable("commentId") Long commentId) {
        return likeService.likeForComment(userId, commentId);
    }
}
