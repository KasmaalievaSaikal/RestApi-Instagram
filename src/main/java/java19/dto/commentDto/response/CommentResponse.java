package java19.dto.commentDto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CommentResponse(

        String comment,
        LocalDate createAt,
        String userName,
        String postTitle,
        int likes
) {

}
