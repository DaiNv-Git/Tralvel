package app.travelstride.Model;

import app.travelstride.Model.BannerImage;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
@Table(name = "banner_group")
public class BannerGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "sub_title")
    private String subTitle;

    @OneToMany(mappedBy = "bannerGroup", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BannerImage> images;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public List<BannerImage> getImages() {
        return images;
    }

    public void setImages(List<BannerImage> images) {
        this.images = images;
    }
}
