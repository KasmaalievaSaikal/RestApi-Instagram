package java19.dto.postDto.request;

import java.util.List;

public record PostRequest(
        String title,
        String description,
        String imageUrl,
        List<Long> collabsUserId) {
}
