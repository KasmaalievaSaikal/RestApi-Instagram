package java19.service.impl;

import java19.configuration.jwt.JwtService;
import java19.dto.SimpleResponse;
import java19.entity.Comment;
import java19.entity.Like;
import java19.entity.Post;
import java19.entity.User;
import java19.excepion.BadCredentialsException;
import java19.excepion.NoSuchElementException;
import java19.excepion.NotFoundException;
import java19.repository.CommentRepository;
import java19.repository.LikeRepository;
import java19.repository.PostRepository;
import java19.repository.UserRepository;
import java19.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final CommentRepository commentRepository;

    @Override
    public SimpleResponse likeForPost(Long userId, Long postId) {
        User currentUser = jwtService.checkAuthentication();
        if (!currentUser.getId().equals(userId)) {
            throw new BadCredentialsException("You are not allowed to like this post!");
        }
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("post not found"));
        List<Like> likeList = likeRepository.findByUserIdAndPostId(userId, postId);
        if (!likeList.isEmpty()) {
            likeRepository.deleteAll(likeList);
            return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Like is removed").build();
        }
        Like like = new Like();
        like.setUser(currentUser);
        like.setPost(post);
        like.setIsLike(true);
        likeRepository.save(like);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("Like is added")
                .build();
    }

    @Override
    public SimpleResponse likeForComment(Long userId, Long commentId) {
        User currentUser = jwtService.checkAuthentication();
        if (!currentUser.getId().equals(userId)) {
            throw new BadCredentialsException("You are not allowed to like this post!");
        }
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NoSuchElementException("comment not found"));
        List<Like> likeList = likeRepository.findByUserIdAndCommentId(userId, commentId);
        if (!likeList.isEmpty()) {
            likeRepository.deleteAll(likeList);
            return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Like is removed").build();
        }
        Like like = new Like();
        like.setUser(currentUser);
        like.setComment(comment);
        like.setIsLike(true);
        likeRepository.save(like);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("Like is added")
                .build();
    }
}
