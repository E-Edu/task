package de.themorpheus.edu.taskservice.cli;

import com.beust.jcommander.Parameter;
import lombok.Data;

@Data
public class Options {

	@Parameter(names = {"--help", "-h"}, help = true, description = "Shows this help/usage page")
	private boolean help;

}
