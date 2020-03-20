package de.themorpheus.edu.cli;

@FunctionalInterface
public interface CommandExecuter<F, T> {
	void execute(F f, T t);
}

