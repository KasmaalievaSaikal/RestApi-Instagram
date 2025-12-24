package java19.api;

import java19.dto.SimpleResponse;
import java19.dto.postDto.request.PostRequest;
import java19.dto.postDto.response.PostResponse;
import java19.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostApi {

    private final PostService postService;

    @PostMapping("/{userId}/create-post")
    public PostResponse createPost(@PathVariable Long userId, @RequestBody PostRequest postRequest) {
        return postService.createPost(userId, postRequest);
    }

    @PutMapping("/{postId}/update-post")
    @PreAuthorize("hasAnyRole('USER')")
    public PostResponse updatePost(@PathVariable Long postId, @RequestBody PostRequest postRequest) {
        return postService.updatePost(postId, postRequest);
    }
    @GetMapping("/{userId}/getByPost")
    public List<PostResponse> getPosts(@PathVariable Long userId) {
        return postService.getUserFeed(userId);
    }

    @DeleteMapping("/{userId}/remove-post")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public SimpleResponse removePost(@PathVariable Long userId, @RequestParam Long postId) {
        return postService.deletePost(userId, postId);
    }
}
