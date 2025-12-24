package java19.service.impl;

import java19.configuration.jwt.JwtService;
import java19.dto.SimpleResponse;
import java19.dto.postDto.request.PostRequest;
import java19.dto.postDto.response.PostResponse;
import java19.entity.Image;
import java19.entity.Like;
import java19.entity.Post;
import java19.entity.User;
import java19.enums.Role;
import java19.excepion.AccessIsDeniedException;
import java19.excepion.BadRequestException;
import java19.excepion.NotFoundException;
import java19.repository.LikeRepository;
import java19.repository.PostRepository;
import java19.repository.UserRepository;
import java19.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    @Override
    public PostResponse createPost(Long userId, PostRequest postRequest) {
        User currentUser = jwtService.checkAuthentication();
        if (currentUser.getId() != userId) {
            throw new AccessIsDeniedException("You are not allowed to create post!");
        }
        Post post = new Post();
        if (postRequest.imageUrl() == null || postRequest.imageUrl().isBlank()) {
            throw new BadRequestException("the post must have image to create post");
        }
        post.setTitle(postRequest.title());
        post.setDescription(postRequest.description());
        post.setCreatedAt(LocalDate.now());
        post.setUser(currentUser);
        if (postRequest.collabsUserId() != null && !postRequest.collabsUserId().isEmpty()) {
            List<User> userCollabs = userRepository.findAllById(postRequest.collabsUserId());
            post.setCollabs(userCollabs);
        }
        Image image = new Image();
        image.setImageUrl(postRequest.imageUrl());
        image.setPost(post);
        post.getImages().add(image);
        postRepository.save(post);
        Like like = new Like();
        like.setIsLike(false);
        like.setPost(post);
        like.setUser(currentUser);
        likeRepository.save(like);
        return PostResponse
                .builder()
                .id(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .username(currentUser.getUsername())
                .collabUsers(post.getCollabs()
                        .stream().map(User::getUsername).toList()).imageUrl(image.getImageUrl())
                .createdAt(post.getCreatedAt()).build();
    }

    @Override
    public PostResponse updatePost(Long postId, PostRequest postRequest) {
        User currentUser = jwtService.checkAuthentication();
        Post post = postRepository.findById(postId).orElseThrow(()
                -> new NotFoundException("post not found"));
        if (!post.getUser().getId().equals(currentUser.getId())) {
            throw new AccessIsDeniedException("You are not allowed to update post!");
        }
        post.setTitle(postRequest.title());
        post.setDescription(postRequest.description());
        postRepository.save(post);
        return PostResponse
                .builder()
                .id(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .username(post.getUser().getUsername())
                .collabUsers(post.getCollabs().stream().map(User::getUsername).
                        toList()).imageUrl(post.getImages().get(0).getImageUrl())
                .createdAt(post.getCreatedAt())
                .build();
    }

    @Override
    public List<PostResponse> getUserFeed(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        List<Long> subscriptionsId = new ArrayList<>();
        if (user.getFollower() != null) {
            subscriptionsId.addAll(user.getFollower().getSubscriptions().stream().map(User::getId).toList());
        }
        subscriptionsId.add(user.getId());
        if (subscriptionsId.isEmpty()) {
            return Collections.emptyList();
        }

        List<Post> postList = postRepository.findAllByIdLikeDescending(subscriptionsId);
        return postList.stream().map(p -> PostResponse
                .builder().id(p.getId())
                .title(p.getTitle())
                .description(p.getDescription())
                .username(p.getUser().getUsername())
                .imageUrl(p.getImages().stream().findFirst().map(Image::getImageUrl).orElse(null)).collabUsers(p.getCollabs().stream().map(User::getUsername).toList())
                .createdAt(p.getCreatedAt())
                .build())
                .toList();
    }

    @Override
    public SimpleResponse deletePost(Long userId, Long postId) {
        User currentUser = jwtService.checkAuthentication();
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException("post not found"));
        if (!post.getUser().getId().equals(currentUser.getId()) && currentUser.getRole() != Role.ADMIN) {
            throw new AccessIsDeniedException("You are not allowed to delete post!");
        }
        post.getUser().getPosts().removeIf(p -> p.getId().equals(postId));
        postRepository.delete(post);
        return SimpleResponse
                .builder()
                .httpStatus(HttpStatus.OK)
                .message("post successfully deleted")
                .build();
    }
}
