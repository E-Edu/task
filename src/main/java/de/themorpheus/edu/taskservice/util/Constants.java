package de.themorpheus.edu.taskservice.util;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class Difficulty {

		public static final String NAME_KEY = "difficulty";

	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class Lecture {

		public static final String NAME_KEY = "lecture";

	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class Module {

		public static final String NAME_KEY = "module";

	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class Subject {

		public static final String NAME_KEY = "subject";

	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class Task {

		public static final String NAME_KEY = "task";

		@NoArgsConstructor(access = AccessLevel.PRIVATE)
		public static class Voting {

			public static final String NAME_KEY = "task_voting";

		}

		@NoArgsConstructor(access = AccessLevel.PRIVATE)
		public static class Done {

			public static final String NAME_KEY = "task_done";

		}

	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class TaskGroup {

		public static final String NAME_KEY = "task_group";

	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class TaskType {

		public static final String NAME_KEY = "task_type";

	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class Solution {

		public static final String NAME_KEY = "solution";

		@NoArgsConstructor(access = AccessLevel.PRIVATE)
		public static class SimpleEquation {

			public static final String NAME_KEY = "simple_equation_solution";

		}

		@NoArgsConstructor(access = AccessLevel.PRIVATE)
		public static class Freestyle {

			public static final String NAME_KEY = "freestyle_solution";

			@NoArgsConstructor(access = AccessLevel.PRIVATE)
			public static class UserSolutions {

				public static final String NAME_KEY = "freestyle_solution_user";

			}

		}

		@NoArgsConstructor(access = AccessLevel.PRIVATE)
		public static class Image {

			public static final String NAME_KEY = "image_solution";

			@NoArgsConstructor(access = AccessLevel.PRIVATE)
			public static class UserSolutions {

				public static final String NAME_KEY = "image_solution_user";

			}

		}

		@NoArgsConstructor(access = AccessLevel.PRIVATE)
		public static class MultipleChoice {

			public static final String NAME_KEY = "multiple_choice_solution";

		}

		@NoArgsConstructor(access = AccessLevel.PRIVATE)
		public static class TopicConnection {

			public static final String NAME_KEY = "topic_connection_solution";

		}

		@NoArgsConstructor(access = AccessLevel.PRIVATE)
		public static class Wordsalad {

			public static final String NAME_KEY = "wordsalad_solution";

		}

	}

	@NoArgsConstructor(access = AccessLevel.PRIVATE)
	public static class UserId {

		public static final UUID TEST_UUID = UUID.fromString("52bd088c-d6ad-4cee-9f93-cabfc8c07234"); //TODO: use real userId

		public static final UUID EMPTY_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");

	}

}
