package app.travelstride;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = "app.travelstride")
@EntityScan(basePackages = "app.travelstride")
@EnableJpaRepositories(basePackages = "app.travelstride")
public class TravelStrideApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelStrideApplication.class, args);
	}

}
