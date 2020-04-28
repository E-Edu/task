package de.themorpheus.edu.taskservice.util;

import java.util.UUID;

public class Constants {

	public static class Difficulty {

		public static final String NAME_KEY = "difficulty";

		private Difficulty() {
		}

	}

	public static class Lecture {

		public static final String NAME_KEY = "lecture";

		private Lecture() {
		}

	}

	public static class Module {

		public static final String NAME_KEY = "module";

		private Module() {
		}

	}

	public static class Subject {

		public static final String NAME_KEY = "subject";

		private Subject() {
		}

	}

	public static class Task {

		public static final String NAME_KEY = "task";

		private Task() {
		}

		public static class Voting {

			public static final String NAME_KEY = "task_voting";

			private Voting() {
			}

		}

		public static class Done {

			public static final String NAME_KEY = "task_done";

			private Done() {
			}

		}

	}

	public static class TaskGroup {

		public static final String NAME_KEY = "task_group";

		private TaskGroup() {
		}

	}

	public static class TaskType {

		public static final String NAME_KEY = "task_type";

		private TaskType() {
		}

	}

	public static class Solution {

		public static final String NAME_KEY = "solution";

		private Solution() {
		}

		public static class SimpleEquation {

			public static final String NAME_KEY = "simple_equation_solution";

			private SimpleEquation() {
			}

		}

		public static class Freestyle {

			public static final String NAME_KEY = "freestyle_solution";

			private Freestyle() {
			}

			public static class UserSolutions {

				public static final String NAME_KEY = "freestyle_solution_user";

				private UserSolutions() {
				}

			}

		}

		public static class Image {

			public static final String NAME_KEY = "image_solution";

			private Image() {
			}

			public static class UserSolutions {

				public static final String NAME_KEY = "image_solution_user";

				private UserSolutions() {
				}

			}

		}

		public static class MultipleChoice {

			public static final String NAME_KEY = "multiple_choice_solution";

			private MultipleChoice() {
			}

		}

		public static class TopicConnection {

			public static final String NAME_KEY = "topic_connection_solution";

			private TopicConnection() {
			}

		}

		public static class Wordsalad {

			public static final String NAME_KEY = "wordsalad_solution";

			private Wordsalad() {
			}

		}

	}

	public static class UserId {

		public static final UUID TEST_UUID = UUID.fromString("52bd088c-d6ad-4cee-9f93-cabfc8c07234");

		public static final UUID EMPTY_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");

		private UserId() {
		}

	}

	private Constants() {
	}

}
