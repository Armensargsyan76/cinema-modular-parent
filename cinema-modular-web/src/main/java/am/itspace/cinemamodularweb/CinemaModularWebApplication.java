package am.itspace.cinemamodularweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@ComponentScan(basePackages = {"am.itspace.cinemamodularweb.*", "am.itspace.cinemamodularcommon.*"})
@EntityScan(basePackages = "am.itspace.cinemamodularcommon.*")
@EnableJpaRepositories(basePackages = "am.itspace.cinemamodularcommon.*")
@EnableAsync
public class CinemaModularWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinemaModularWebApplication.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
