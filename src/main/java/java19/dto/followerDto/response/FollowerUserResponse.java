package java19.dto.followerDto.response;

import lombok.Builder;

@Builder
public record FollowerUserResponse(
        String userName,
        String image) {
}
