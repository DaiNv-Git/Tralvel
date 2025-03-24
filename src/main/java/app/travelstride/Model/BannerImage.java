package app.travelstride.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "banner_image")
public class BannerImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image_url")
    private String imageUrl;

    private Integer sequence;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "banner_group_id")
    private BannerGroup bannerGroup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public BannerGroup getBannerGroup() {
        return bannerGroup;
    }

    public void setBannerGroup(BannerGroup bannerGroup) {
        this.bannerGroup = bannerGroup;
    }
}
