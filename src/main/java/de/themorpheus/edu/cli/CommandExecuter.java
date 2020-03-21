package de.themorpheus.edu.cli;

@FunctionalInterface
public interface CommandExecuter<F, S, T> {
	void execute(F f, S s, T t);
}

