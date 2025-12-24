package java19.service;

import java19.dto.SimpleResponse;

public interface LikeService {

    SimpleResponse likeForPost(Long userId, Long postId);

    SimpleResponse likeForComment(Long userId, Long commentId);
}
