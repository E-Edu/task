package de.themorpheus.edu.taskservice.cli;

import com.beust.jcommander.JCommander;
import de.themorpheus.edu.taskservice.cli.commands.HelpCommand;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CLIManager {

	private static final List<Command<?>> COMMANDS = new ArrayList<>();
	private static final Logger LOGGER = LoggerFactory.getLogger(CLIManager.class.getSimpleName());

	@SuppressWarnings(value = {"unchecked", "resource"})
	public static void initCli(String... args) {
		JCommander.Builder builder = JCommander.newBuilder().acceptUnknownOptions(true).programName("E-Edu");

		try {
			Command<HelpCommand> command = new Command<HelpCommand>("help", "show important Commands", HelpCommand::execute, HelpCommand.class, "h");
			COMMANDS.add(command);
		} catch (InstantiationException | IllegalAccessException ex) {
			LOGGER.error("Error occurred while creating commands.", ex);
		}

		for (Command<?> c : COMMANDS) builder.addCommand(c.getName(), c, c.getAlias());

		Options options = new Options();
		JCommander commander = builder.addObject(options).build();

		String line = null;
		Scanner scanner = new Scanner(System.in);

		while (!Thread.currentThread().isInterrupted() && scanner.hasNextLine()) {
			line = scanner.nextLine();
			commander.parse(line.split(" "));

			String cmdName = commander.getParsedCommand();
			Optional<Command<?>> optCommand = COMMANDS.stream().filter(c -> c.getName().equalsIgnoreCase(cmdName)).findAny();

			if (optCommand.isPresent()) {
				Command<?> command = optCommand.get();
				CommandExecuter<JCommander, Options, Object> executor = (CommandExecuter<JCommander, Options, Object>) command.getExecuter();
				executor.execute(commander, options, command.getArgs());
			} else {
				commander.usage();
			}
		}
	}

}
