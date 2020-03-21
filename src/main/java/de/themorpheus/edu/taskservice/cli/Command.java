package de.themorpheus.edu.taskservice.cli;

import com.beust.jcommander.JCommander;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Command<T> {

	private final String name;
	private final String description;
	private final String[] alias;
	private final CommandExecuter<JCommander, Options, T> executer;
	private T args;

	public Command(String name, String description, CommandExecuter<JCommander, Options, T> executer, Class<T> args, String... alias) throws InstantiationException, IllegalAccessException {
		this(description, description, alias, executer);
		this.args = args.newInstance();
	}

}
