package com.KaizakiSoftwares.JobPortal;

import com.KaizakiSoftwares.JobPortal.role.Role;
import com.KaizakiSoftwares.JobPortal.role.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.ArrayList;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
public class JobPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobPortalApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(RoleRepository roleRepository) {
		return args -> {
			ArrayList<String> userRoles = new ArrayList<>(3);
			userRoles.add("APPLICANT");
			userRoles.add("COMPANY");
			userRoles.add("ADMIN");

            for (String role : userRoles) {
                if (roleRepository.findByName(role).isEmpty()) {
                    {
                        roleRepository.save(
                                Role.builder().name(role).build()
                        );
                    }
                }
//            if(roleRepository.findByName("USER").isEmpty()){
//				roleRepository.save(
//						Role.builder().name("USER").build()
//				);
            }
		};
	}

}
