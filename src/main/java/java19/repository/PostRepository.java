package java19.repository;

import java19.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p where p.user.id in :usersId order by p.createdAt desc")
    List<Post> findAllByIdLikeDescending(List<Long> usersId);
}
