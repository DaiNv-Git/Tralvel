package app.travelstride.Service;

import app.travelstride.Model.Activity;
import app.travelstride.Model.Jpa.ActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityService {
    @Autowired
    private  ActivityRepository activityRepository;

    public List<Activity> getAll() {
        return activityRepository.findAll();
    }

    public Activity create(Activity activity) {
        return activityRepository.save(activity);
    }

    public Activity update(Long id, Activity activity) {
        Activity old = activityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Activity not found"));
        old.setActivity(activity.getActivity());
        return activityRepository.save(old);
    }

    public void delete(Long id) {
        activityRepository.deleteById(id);
    }
}
