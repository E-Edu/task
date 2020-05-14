package com.gewia.taskservice;

import com.gewia.taskservice.util.GitInfo;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import io.sentry.Sentry;
import io.sentry.SentryClient;

@EnableJpaRepositories
@SpringBootApplication(scanBasePackages = "com.gewia.taskservice")
public class TaskServiceApplication {

	public static final boolean PRODUCTIVE = Boolean.parseBoolean(System.getenv("PRODUCTIVE"));
	public static final boolean SENTRY_ENABLED = Boolean.parseBoolean(System.getenv("SENTRY_ENABLED"));
	public static final boolean INFLUX_ENABLED = Boolean.parseBoolean(System.getenv("INFLUX_ENABLED"));
	public static final int MAX_TASK_RESULTS = Integer.parseInt(System.getenv("MAX_TASK_RESULTS"));
	public static final int MAX_TASK_GROUP_RESULTS = Integer.parseInt(System.getenv("MAX_TASK_GROUP_RESULTS"));

	private static final Logger LOGGER = LoggerFactory.getLogger(TaskServiceApplication.class.getSimpleName());

	public static void main(String[] args) {
		initSentry();

		SpringApplication.run(TaskServiceApplication.class, args);

		LOGGER.info("Influx enabled: {}", INFLUX_ENABLED);
	}

	private static void initSentry() {
		if (!PRODUCTIVE && !SENTRY_ENABLED) {
			Sentry.init("");
			LOGGER.info("Sentry error reporting is disabled.");
			return;
		}

		Sentry.init("https://00da4d4d3a8b4f1d8105d2d15e81a88c@sentry.the-morpheus.de/8");
		SentryClient sentry = Sentry.getStoredClient();
		sentry.addTag("OS", System.getProperty("OS"));
		sentry.addTag("version", TaskServiceApplication.class.getPackage().getImplementationVersion());
		sentry.addTag("title", TaskServiceApplication.class.getPackage().getImplementationTitle());
		sentry.addTag("environment", PRODUCTIVE ? "production" : "dev");

		try {
			GitInfo gitInfo = GitInfo.load();
			sentry.addTag("build-host", gitInfo.getBuildHost());
			sentry.addTag("build-time", gitInfo.getBuildTime());
			sentry.addTag("build-user-email", gitInfo.getBuildUserEmail());
			sentry.addTag("build-version", gitInfo.getBuildVersion());
			sentry.addTag("git-branch", gitInfo.getBranch());
			sentry.addTag("git-commit-id", gitInfo.getCommitId());
			sentry.addTag("git-commit-message", gitInfo.getCommitMessageShort());
			sentry.addTag("git-commit-user-email", gitInfo.getCommitUserEmail());
			sentry.addTag("git-dirty", gitInfo.getGitDirty());
		} catch (IOException ex) {
			LOGGER.error("Error while loading git information", ex);
		}

		LOGGER.info("Sentry error reporting is enabled.");
	}

}
