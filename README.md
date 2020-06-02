![Build](https://github.com/Gewia/task-ms/workflows/Build/badge.svg?style=flat&logo=appveyor)
![GitHub](https://img.shields.io/github/license/Gewia/task-ms)
[![GitHub contributors](https://img.shields.io/github/contributors/Gewia/task-ms.svg?style=flat)](https://GitHub.com/Gewia/task-ms/graphs/contributors/)
[![Discord](https://img.shields.io/discord/691078100272021514?color=7289DA&label=discord&logo=discord&logoColor=white)](https://discord.gg/XW5jzs6)
[![Website](https://img.shields.io/website?down_color=red&down_message=offline&up_message=online&url=https%3A%2F%2Ftask.gewia.com)](https://task.gewia.com)

# Gewia Task Microservice

This projects contains the source code for the task microservice which
is responsible for everything related to tasks in the Gewia system.

## How to build
Run:
```sudo docker build --tag eedu/taskms:latest .```

## How to start the docker stack
Run:
```sudo docker-compose up```

or in detached mode:
```sudo docker-compose up -d```

## Contribute

You want to contribute to this project? Great!

[Drafts & Documentation](https://github.com/E-Edu/draft-documents) <br>
[Git Workflow (German)](https://github.com/E-Edu/general/blob/master/guides/conventions.md) <br>
[Code Style (German)](https://github.com/E-Edu/general/blob/master/guides/conventions.md#code-style)

## Sentry error report

By default this project **won't** send any data to our central Sentry instance!
To enable this feature just set the production variable to "true" or create a file in your root directory called ".sentryEnabled", but
**never** commit this file!

The transmitted data includes information about your environment:
* Username (e.g. "steve")
* Current git branch (e.g. "experimental")
* Build time
* Build user email
* Build version
* Commit id
* Commit message (short)
* Commit user email
* Git "dirty" property

## License

This project is licensed under the `GNU General Public License v3.0`<br>
See `LICENSE` for more information