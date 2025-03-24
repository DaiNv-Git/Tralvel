package app.travelstride.Model.Jpa;

import app.travelstride.Model.Post;
import app.travelstride.Model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {
}
