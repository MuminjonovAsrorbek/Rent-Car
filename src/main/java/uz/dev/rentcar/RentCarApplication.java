package uz.dev.rentcar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RentCarApplication {

    public static void main(String[] args) {
        SpringApplication.run(RentCarApplication.class, args);
    }

}
