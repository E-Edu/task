package de.themorpheus.edu.taskservice;

import de.themorpheus.edu.taskservice.cli.CLIManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication(scanBasePackages = "de.themorpheus.edu.taskservice")
public class TaskServiceApplication {

	public static final boolean PRODUCTIVE = Boolean.getBoolean(System.getenv("PRODUCTIVE"));

	public static void main(String[] args) {

		SpringApplication.run(TaskServiceApplication.class, args);

		CLIManager.initCli(args);

	}

}
