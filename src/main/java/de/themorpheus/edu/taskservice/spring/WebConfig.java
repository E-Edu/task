package de.themorpheus.edu.taskservice.spring;

import de.themorpheus.edu.taskservice.TaskServiceApplication;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		List<String> origins = new ArrayList<>(Arrays.asList(
			"e-edu.the-morpheus.de",
			"task.e-edu.the-morpheus.de"
		));

		if (!TaskServiceApplication.PRODUCTIVE) {
			origins.add("localhost");
			origins.add("editor.swagger.io/");
		}

		registry.addMapping("/**")
			.allowedOrigins(
				origins.toArray(new String[0])
			)
			.allowedMethods("GET", "POST", "PATCH", "PUT", "DELETE")
			.allowCredentials(false).maxAge(3600);
	}

}
