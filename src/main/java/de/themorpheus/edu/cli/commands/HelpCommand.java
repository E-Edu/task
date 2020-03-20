package de.themorpheus.edu.cli.commands;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "Shows the help/usage page")
public class HelpCommand {
	public static void execute(JCommander commander, HelpCommand cmd) {
		System.out.println("Test");
		commander.usage();
	}
}
