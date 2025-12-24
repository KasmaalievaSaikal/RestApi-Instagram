package java19.service.impl;

import jakarta.transaction.Transactional;
import java19.configuration.jwt.JwtService;
import java19.dto.SimpleResponse;
import java19.dto.commentDto.request.CommentRequest;
import java19.dto.commentDto.response.CommentResponse;
import java19.entity.Comment;
import java19.entity.Like;
import java19.entity.Post;
import java19.entity.User;
import java19.enums.Role;
import java19.excepion.AccessIsDeniedException;
import java19.excepion.BadRequestException;
import java19.excepion.NoSuchElementException;
import java19.excepion.NotFoundException;
import java19.repository.CommentRepository;
import java19.repository.LikeRepository;
import java19.repository.PostRepository;
import java19.repository.UserRepository;
import java19.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final JwtService jwtService;


    @Override
    public CommentResponse createComment(Long userId, Long postId, CommentRequest commentRequest) {
        User currentUser = jwtService.checkAuthentication();
        if (!currentUser.getId().equals(userId)) {
            throw new BadRequestException("You can't to comment for this user");
        }
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("Post not found"));
        Comment comment = new Comment();
        comment.setComment(commentRequest.comment());
        comment.setCreatedAt(LocalDate.now());
        comment.setUser(currentUser);
        comment.setPost(post);
        commentRepository.save(comment);

        Like like = new Like();
        like.setUser(currentUser);
        like.setPost(post);
        like.setComment(comment);
        like.setIsLike(false);
        comment.getLikes().add(like);
        likeRepository.save(like);
        long countLike = comment.getLikes().stream().filter(Like::getIsLike).count();
        return CommentResponse
                .builder()
                .comment(comment.getComment())
                .userName(comment.getUser().getUsername())
                .postTitle(comment.getPost().getTitle())
                .likes((int) countLike)
                .createAt(comment.getCreatedAt())
                .build();
    }

    @Override
    public List<CommentResponse> findAllCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("Post not found"));
        List<Comment> comments = commentRepository.findAllByPostId(postId);
        if (comments.isEmpty()) {
            throw new NotFoundException("comment is not found");
        }
        List<CommentResponse> list = comments.stream().map(c -> {
            long countLike = likeRepository.findAll().stream().filter(Like::getIsLike).count();
            return CommentResponse
                    .builder()
                    .comment(c.getComment())
                    .userName(c.getUser().getUsername())
                    .postTitle(c.getPost().getTitle())
                    .likes((int) countLike)
                    .createAt(c.getCreatedAt())
                    .build();
        }).toList();
        return list;
    }

    @Override
    public SimpleResponse deleteCommentByiD(Long commentId) {
        User currentUser = jwtService.checkAuthentication();
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NoSuchElementException("Comment not found"));
        if (!comment.getUser().getId().equals(currentUser.getId()) && currentUser.getRole() != Role.ADMIN) {
            throw new AccessIsDeniedException();
        }
        commentRepository.deleteById(commentId);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("comment is removed")
                .build();
    }
}