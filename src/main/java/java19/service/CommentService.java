package java19.service;

import java19.dto.SimpleResponse;
import java19.dto.commentDto.request.CommentRequest;
import java19.dto.commentDto.response.CommentResponse;

import java.util.List;

public interface CommentService {

    CommentResponse createComment(Long userId, Long postId, CommentRequest commentRequest);

    List<CommentResponse> findAllCommentsByPostId(Long postId);

    SimpleResponse deleteCommentByiD(Long commentId);

}
