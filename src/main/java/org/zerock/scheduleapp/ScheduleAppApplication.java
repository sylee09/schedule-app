package org.zerock.scheduleapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class ScheduleAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleAppApplication.class, args);
    }

}
