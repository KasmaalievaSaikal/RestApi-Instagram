package java19.service;

import java19.dto.SimpleResponse;
import java19.dto.postDto.request.PostRequest;
import java19.dto.postDto.response.PostResponse;

import java.util.List;

public interface PostService {

    PostResponse createPost(Long userId, PostRequest postRequest);

    PostResponse updatePost(Long postId, PostRequest postRequest);

    List<PostResponse> getUserFeed(Long userId);

    SimpleResponse deletePost(Long userId, Long postId);
}

