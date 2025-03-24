package app.travelstride.Model.Jpa;

import app.travelstride.Model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p " +
            "WHERE (:keyword IS NULL OR p.title LIKE %:keyword%) " +
            "AND (:typeId IS NULL OR p.types LIKE %:typeId%)")
    Page<Post> search(@Param("keyword") String keyword, @Param("typeId") String typeId, Pageable pageable);

    List<Post> findByIsShow(boolean isShow);
}
