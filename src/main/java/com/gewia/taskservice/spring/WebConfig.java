package com.gewia.taskservice.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gewia.taskservice.TaskServiceApplication;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

	@Autowired private ObjectMapper mapper;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		List<String> origins = new ArrayList<>(Arrays.asList(
			"gewia.the-morpheus.de",
			"task.gewia.the-morpheus.de"
		));

		if (!TaskServiceApplication.PRODUCTIVE) {
			origins.add("localhost");
			origins.add("localhost:3000");
			origins.add("editor.swagger.io/");
		}

		registry.addMapping("/**")
			.allowedOrigins(
				origins.toArray(new String[0])
			)
			.allowedMethods("GET", "POST", "PATCH", "PUT", "DELETE")
			.allowCredentials(false).maxAge(3600);
	}

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.stream()
				.filter(x -> x instanceof MappingJackson2HttpMessageConverter)
				.forEach(x -> ((MappingJackson2HttpMessageConverter) x).setObjectMapper(this.mapper));
	}

}
