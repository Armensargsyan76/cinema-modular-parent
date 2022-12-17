package am.itspace.cinemamodularrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"am.itspace.cinemamodularrest.*", "am.itspace.cinemamodularcommon.*"})
@EntityScan(basePackages = "am.itspace.cinemamodularcommon.*")
@EnableJpaRepositories(basePackages = "am.itspace.cinemamodularcommon.*")
public class CinemaModularRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinemaModularRestApplication.class, args);
    }

}
