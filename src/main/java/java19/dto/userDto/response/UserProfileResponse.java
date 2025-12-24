package java19.dto.userDto.response;

import java19.dto.postDto.response.PostResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record UserProfileResponse(
        String username,
        String fullName,
        String image,
        int subscribers,
        int subscriptions,
        List<PostResponse> posts) {
}
