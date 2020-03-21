package de.themorpheus.edu.taskservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.themorpheus.edu.taskservice.cli.CLIManager;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication(scanBasePackages = "de.themorpheus.edu.taskservice")
public class TaskServiceApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(TaskServiceApplication.class, args);

		CLIManager.initCli(args);
		
	}

}
