package java19.dto.followerDto.response;

import lombok.Builder;

@Builder
public record ProfileSubscribeResponse(
        Long targetUserId,
        boolean subscribed,
        String button) {
}