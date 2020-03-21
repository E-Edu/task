package de.themorpheus.edu.taskservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.themorpheus.edu.cli.CLIManager;

@SpringBootApplication
public class TaskServiceApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(TaskServiceApplication.class, args);
		
		CLIManager.initCli(args);
		
	}

}
