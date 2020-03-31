package de.themorpheus.edu.taskservice.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import java.io.IOException;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitInfo {

	@JsonProperty(value = "git.branch")
	private String branch;
	@JsonProperty("git.build.host")
	private String buildHost;
	@JsonProperty("git.build.time")
	private String buildTime;
	@JsonProperty("git.build.user.email")
	private String buildUserEmail;
	@JsonProperty("git.build.version")
	private String buildVersion;
	@JsonProperty("git.commit.id")
	private String commitId;
	@JsonProperty("git.commit.message.short")
	private String commitMessageShort;;
	@JsonProperty("git.commit.user.email")
	private String commitUserEmail;
	@JsonProperty("git.dirty")
	private String gitDirty;

	public static GitInfo load() throws IOException {
		Resource resource = new ClassPathResource("git.properties");
		return new ObjectMapper().readValue(resource.getInputStream(), GitInfo.class);
	}

}
