package java19.dto.postDto.response;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record PostResponse(
        Long id,
        String title,
        String description,
        String username,
        String imageUrl,
        List<String> collabUsers,
        LocalDate createdAt) {
}
