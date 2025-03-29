package app.travelstride.Model.Jpa;


import app.travelstride.Model.BannerGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerGroupRepository extends JpaRepository<BannerGroup, Long> {
    
}