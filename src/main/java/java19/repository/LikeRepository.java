package java19.repository;

import java19.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    List<Like> findByUserIdAndPostId(Long userId, Long postId);

    List<Like> findByUserIdAndCommentId(Long userId, Long commentId);
}
