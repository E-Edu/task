package de.themorpheus.edu.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.beust.jcommander.JCommander;

import de.themorpheus.edu.cli.commands.HelpCommand;

public class CLIManager {

	private static final List<Command<?>> commands = new ArrayList<>();
	
	@SuppressWarnings(value = {"unchecked", "resource"})
	public static void initCli(String... args) {
		
		JCommander.Builder builder = JCommander.newBuilder().acceptUnknownOptions(true).programName("E-Edu");
		
		try {
			Command<HelpCommand> command = new Command<HelpCommand>("help", "show important Commands", HelpCommand::execute, HelpCommand.class, "h");
			commands.add(command);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		for (Command<?> c : commands) builder.addCommand(c.getName(), c, c.getAlias());
			
		Options options = new Options();
		JCommander commander = builder.addObject(options).build();
		
		String line = null;
		Scanner scanner = new Scanner(System.in);
		
		while (!Thread.currentThread().isInterrupted()) {
			line = scanner.nextLine();
			commander.parse(line.split(" "));
			
			String cmdName = commander.getParsedCommand();
			Optional<Command<?>> optCommand = commands.stream().filter(c -> c.getName().equalsIgnoreCase(cmdName)).findAny();
			
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
