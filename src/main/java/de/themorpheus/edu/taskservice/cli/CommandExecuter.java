package de.themorpheus.edu.taskservice.cli;

@FunctionalInterface
public interface CommandExecuter<F, S, T> {
	void execute(F f, S s, T t);
}

