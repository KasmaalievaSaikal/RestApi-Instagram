package java19.service;

import java19.dto.followerDto.response.FollowerUserResponse;
import java19.dto.followerDto.response.ProfileSubscribeResponse;

import java.util.List;

public interface FollowerService {

    List<FollowerUserResponse> search(String name);

    ProfileSubscribeResponse subscribe(Long targetId);

    List<FollowerUserResponse> getAllSubscribedByUserId(Long userId);

    List<FollowerUserResponse> getAllSubscriptionsByUserId(Long userId);
}
