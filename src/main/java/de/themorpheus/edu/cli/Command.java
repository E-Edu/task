package de.themorpheus.edu.cli;

import com.beust.jcommander.JCommander;

import lombok.Data;

@Data
public class Command<T> {
	
	private String name;
	private String description;
	private String[] alias;
	private T args;
	private CommandExecuter<JCommander, T> executer;
	
	public Command(String name, String description, CommandExecuter<JCommander, T> executer,  Class<T> args, String... alias) throws InstantiationException, IllegalAccessException {
		this.name = name;
		this.description = description;
		this.alias = alias;
		this.args = args.newInstance();
		this.executer = executer;
	}
}

