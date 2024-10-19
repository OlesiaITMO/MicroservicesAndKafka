package org.itmo_olesia.presentationmicroservice;

import lombok.RequiredArgsConstructor;
import org.itmo_olesia.dto.DTO.UserDTO;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.context.SecurityContextHolder;


@RequiredArgsConstructor
@SpringBootApplication
public class PresentationMicroserviceApplication {

    public static void main(String[] args) {

        SpringApplication.run(PresentationMicroserviceApplication.class, args);

    }

}
