package de.themorpheus.edu.taskservice.util;

import java.util.UUID;

public class Constants {

	public static class Difficulty {

		public static final String NAME_KEY = "difficulty";

	}

	public static class Lecture {

		public static final String NAME_KEY = "lecture";

	}

	public static class Module {

		public static final String NAME_KEY = "module";

	}

	public static class Subject {

		public static final String NAME_KEY = "subject";

	}

	public static class Task {

		public static final String NAME_KEY = "task";

	}

	public static class TaskGroup {

		public static final String NAME_KEY = "task_group";

	}

	public static class TaskType {

		public static final String NAME_KEY = "task_type";

	}

	public static class Solution {

		public static final String NAME_KEY = "solution";

		public static class SimpleEquation {

			public static final String NAME_KEY = "simple_equation_solution";

		}

		public static class Freestyle {

			public static final String NAME_KEY = "freestyle_solution";

		}

		public static class Image {

			public static final String NAME_KEY = "image_solution";

		}

		public static class MultipleChoice {

			public static final String NAME_KEY = "multiple_choice_solution";

		}

		public static class TopicConnection {

			public static final String NAME_KEY = "topic_connection_solution";

		}

		public static class Wordsalad {

			public static final String NAME_KEY = "wordsalad_solution";

		}

	}

	public static class UserId {

		public static final UUID TEST_UUID = UUID.fromString("52bd088c-d6ad-4cee-9f93-cabfc8c07234"); //TODO: use real userId

		public static final UUID EMPTY_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");

	}

}
