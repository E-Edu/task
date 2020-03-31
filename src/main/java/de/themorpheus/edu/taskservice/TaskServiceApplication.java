package de.themorpheus.edu.taskservice;

import de.themorpheus.edu.taskservice.cli.CLIManager;
import de.themorpheus.edu.taskservice.util.GitInfo;
import io.sentry.Sentry;
import io.sentry.SentryClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import java.io.IOException;

@EnableJpaRepositories
@SpringBootApplication(scanBasePackages = "de.themorpheus.edu.taskservice")
public class TaskServiceApplication {

	public static final boolean PRODUCTIVE = Boolean.parseBoolean(System.getenv("PRODUCTIVE"));
	public static final boolean SENTRY_ENABLED = Boolean.parseBoolean(System.getenv("SENTRY_ENABLED"));

	private static final Logger LOGGER = LoggerFactory.getLogger(TaskServiceApplication.class.getSimpleName());

	public static void main(String[] args) {
		initSentry();

		SpringApplication.run(TaskServiceApplication.class, args);

		CLIManager.initCli(args);

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
