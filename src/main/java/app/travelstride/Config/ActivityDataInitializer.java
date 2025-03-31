package app.travelstride.Config;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@Component
public class ActivityDataInitializer implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) {
        String checkDataQuery = "SELECT COUNT(*) FROM activity";
        Integer count = jdbcTemplate.queryForObject(checkDataQuery, Integer.class);

        if (count != null && count == 0) {
            String insertQuery = "INSERT INTO activity (activity) VALUES " +
                    "('Cycling & Biking')," +
                    "('Small Ship Cruises')," +
                    "('Safari')," +
                    "('River Cruises')," +
                    "('Climbing and Mountaineering')," +
                    "('Trekking & Camping')," +
                    "('Hiking & Walking')," +
                    "('Train & Rail')," +
                    "('River Rafting')," +
                    "('Yoga, Meditation & Wellness')";
            jdbcTemplate.execute(insertQuery);
            System.out.println("Inserted default activities.");
        } else {
            System.out.println("Activities already exist. Skipping initialization.");
        }
    }
}
