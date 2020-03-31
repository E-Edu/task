package de.themorpheus.edu.taskservice;

import de.themorpheus.edu.taskservice.cli.CLIManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication(scanBasePackages = "de.themorpheus.edu.taskservice")
public class TaskServiceApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(TaskServiceApplication.class.getSimpleName());

	public static final boolean PRODUCTIVE = Boolean.getBoolean(System.getenv("PRODUCTIVE"));

	public static void main(String[] args) {

		SpringApplication.run(TaskServiceApplication.class, args);

		CLIManager.initCli(args);

		System.out.println("LEL");
		LOGGER.info("Initialized Task-MicroService");
		LOGGER.warn("Initialized Task-MicroService");
		LOGGER.error("Initialized Task-MicroService");

	}

}
