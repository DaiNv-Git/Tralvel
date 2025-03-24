package app.travelstride.Service;

import app.travelstride.Model.BannerGroup;
import app.travelstride.Model.Jpa.BannerGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerService {
    @Autowired
    private BannerGroupRepository bannerGroupRepository;

    public List<BannerGroup> getAllBanners() {
        return bannerGroupRepository.findAll();
    }

    public BannerGroup saveBanner(BannerGroup bannerGroup) {
        return bannerGroupRepository.save(bannerGroup);
    }

    public BannerGroup getBannerById(Long id) {
        return bannerGroupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Banner not found"));
    }

    public void deleteBanner(Long id) {
        bannerGroupRepository.deleteById(id);
    }
}

