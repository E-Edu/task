package com.gewia.taskservice.spring;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;

/**
 * Transforms the model class name to following format: <i>prefix_camelCase</i> .
 * Example: TaskTypeModel -> task_taskType
 */
public class PrefixNamingStrategy extends SpringPhysicalNamingStrategy {

	private static final String TABLE_PREFIX = "task_";

	private static final Pattern REPLACE_PATTERN = Pattern.compile("(Model)");
	private static final Set<String> RESERVED = new HashSet<>(Collections.singletonList("DTYPE"));

	@Override
	public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment context) {
		return name;
	}

	@Override
	public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment context) {
		return name;
	}

	@Override
	public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment context) {
		return name;
	}

	@Override
	public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
		return name;
	}

	@Override
	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
		return apply(name);
	}

	private static Identifier apply(Identifier name) {
		if (name == null) return null;
		if (RESERVED.contains(name.getText())) return name;

		String tableName = apply(name.getText());
		return new Identifier(tableName, name.isQuoted());
	}

	private static String apply(String original) {
		String name = original;
		name = removeUnnecessary(name); // removes "Model" from the name
		name = Character.toLowerCase(name.charAt(0)) + name.substring(1); // make first char lowercase
		name = TABLE_PREFIX + name;
		return name;
	}

	private static String removeUnnecessary(String s) {
		return REPLACE_PATTERN.matcher(s).replaceAll("");
	}

}
