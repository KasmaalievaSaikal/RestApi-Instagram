package java19.api;


import java19.dto.SimpleResponse;
import java19.dto.commentDto.request.CommentRequest;
import java19.dto.commentDto.response.CommentResponse;
import java19.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentApi {
    private final CommentService commentService;

    @PostMapping("/{userId}/{postId}/create-comment")
    public CommentResponse createComment(@PathVariable("userId") Long userId,
                                         @PathVariable("postId") Long postId,
                                         @RequestBody CommentRequest commentRequest) {
        return commentService.createComment(userId, postId, commentRequest);
    }

    @GetMapping("/{postId}/getComments")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<CommentResponse> getComments(@PathVariable Long postId) {
        return commentService.findAllCommentsByPostId(postId);
    }

    @DeleteMapping("/{commentId}/delete-comment")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public SimpleResponse deleteCommentByiD(@PathVariable Long commentId) {
        return commentService.deleteCommentByiD(commentId);
    }
}
