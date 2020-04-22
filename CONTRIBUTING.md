# Contributing

Great if you want to contribute to this project!

But we have to maintain this project and to do that easier everyone has to observe the follwing rules.

## Code Style

The code is written like the official [Java Code Conventions](https://www.oracle.com/technetwork/java/codeconventions-150003.pdf).

Important is to give the methods and classes expressive and clear names to easily differentiate between them.

A command should be wrapped in a new line if it has more than 120 characters.

Tab characters have to be used instead of spaces.

All not final fields have to be tagged with `@Autowired`.

Getters and Setters are generated with the annotation `@Data` by the plugin *lombok*.
Constructors with ``@NoArgsConstructor`` and `@AllArgsConstructor`.

### Variable names

In ``ControllerResult`` is Controller just a prefix so exclude it in the variable name.

In any ``Model`` is Model a prefix so exclude it.

Example:
````java
ControllerResult<SolutionModel> solutionResult = this.solutionController.getSolution(taskId);
````

DTOs are named if they are are requestDTO ``dto``, if it is a responseDTO they are called `responseDTO`. 

(`ResponseDTOModel` is named `responseDTOModel` )

## Structure

### Package structure

The base package is `de.themorpheus.edu.taskservice`

This package includes:
- controller<br>
   └ controller of endpoints
- database<br>
   └ repositories of controllers and models of those repositories
- endpoint<br>
   └ endpoint classes and response/request DTOs (**D**ata**T**ransfer**O**bject)
- pubsub<br>
   └ pubsub classes and subscriber/publisher for the [PubSubSystem](https://github.com/E-Edu/concept/blob/master/documentation/dev/architecture/architecture.md#pubsub-cluster)
- spring<br>
   └ custom config files of spring
- util<br>
   └ those who don't match to any other package (like Errors, Constants, etc.)

---

### Arrangement of methods

To keep a clean code the methods have to be sorted like the following:
- Create
- Update
- Get
  - GetAll
  - GetSpecific
  - ...
- Check
- Delete
- other methods (e.g. methods in other controllers)

---

### Endpoints

The endpoint classes have to define the route and call the associated method in the controller. 
They also have to handle the authentication and authorization of the users and check permissions.
Every endpoint class has to end with the suffix `Endpoint`.

#### DTOs

##### Validation

**D**ata**T**ransfer**O**bjects have to validate the *Requestbodies* with `javax.validation` annotations.
Here is a quick overview:

|Annotation|Function|Applicable to|
|---|---|---|
|@NotNull|Can't be null|-|
|@NotEmpty|Can't be empty or null|String, Collection, Map, Array|
|@NotBlank|Trimmed string can't be empty|Text values|
|@Min|Value can't be lower than parameter|-|
|@Max|Value can't be greater than parameter|-|
|@Size|Size of field has to be within the given parameter|String, Collection, Map, Array|

##### Annotations

Every DTO has to be annotated with `@Data`, `@NoArgsConstructor` and `@AllArgsConstructor`. (Please also in this order)

Example:
```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTaskRequestDTO {
```

##### Class name

The DTO class name has to be the action what it does and if it is a 
request DTO `Request` suffix or a response dto a `Response` suffix. At least the name has to end with the suffix `DTO`.

Example:
```java
public class GetAllSubjectsResponseDTO {
```

##### Conversions

If you have to convert a DTO you are free to add a method in this class so that the code stays clean

##### DTOModel

Sometimes you have to create a new class because you return e.g. a List. Just create a new Class but add the suffix `Model`.

Example:
```java
public class CheckMultipleChoiceSolutionsResponseDTO {

	@NotNull @Size(min = 1)
	private List<CheckMultipleChoiceSolutionsResponseDTOModel> solutions;
```

#### Particularities

##### Class annotations

Every endpoint class needs the annotation `@Timed` and `@RestController`.

Example:
```java
@Timed
@RestController
public class TaskEndpoint {
```

##### Mappings

In the endpoint you have use the specific mapping and not ~~`@RequestMapping`~~.

Example:
```java
@PostMapping("/task")
public Object createTask(....
```

##### Returning

If you call a method in a controller it returns a `ControllerResult`. 
You have to call the method `.getHttpResponse();` to get a correct *HttpResponse*.
The method has to return `Object`.

Example:
```java
public Object deleteTask(@PathVariable @Min(1) int taskId) {
	return this.taskController.deleteTask(taskId).getHttpResponse();
}
```

##### Pathvariables

Pathvariables have to be written in the route with Braces `{}` and 
named equal as parameter in the method and have to be annotated with `@PathVariable`.

Example: 
```java
@GetMapping("/task/{taskId}")
public Object getTask(@PathVariable int taskId) {
```

##### Requestbodies

Requestbodies have to be tagged with `@RequestBody` and `@Valid` and have to be named `dto`

Example:
```java
public Object createTask(@RequestBody @Valid CreateTaskRequestDTO dto) {
```

---

### Controller

Controllers have to handle the access to the repository. 
The controllers have to get the data from the repository, check them and return them.
Controllers have to end with the suffix `Controller`.

**Is is important to access the repository as few as possible, but you should also check if something at all exists!**

#### Particularities

##### Class annotations

Every endpoint class needs the annotation `@Component`.

Example:
```java
@Component
public class TaskController {
```

##### Returning

Every method in Controller has to return an instance of `ControllerResult`.

Example:
```java
public ControllerResult<TaskModel> createTask(CreateTaskRequestDTO dto) {
```

##### Repositories

Most of the Controller need repositories. They have to get annotated with `@Autowired`.

Is is forbidden to access a different repository as what it isn't meant for.

Instead of accessing a different repository you can access the controller of this repository.

#### ControllerResult

Every method in a Controller has to return a `ControllerResult` instance.
On the One Hand you can hand over a Result on the other hand if something went wrong an Error.

`ControllerResult` has several methods to check if there is an Error, Result, etc. to check if e.g. an Error occurred
while accessing another Controller. If this happens you can call `ControllerResult.ret` and insert the previous `ControllerResult`.

Example:
```java
ControllerResult<SolutionModel> solutionResult = this.solutionController.getSolution(taskId);
if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);
```

##### NAME_KEY

Every Controller has to `import static` a String `NAME_KEY` in the class `Constants`. It is used to identify where an Error occurred.

Example:
```java
import static de.themorpheus.edu.taskservice.util.Constants.Task.NAME_KEY;
```

##### Errors

If you e.g. check something that returned from a query of a database but theres no Result you have to throw an Error. 
To identify the error, as mentioned above, you also have to return as extra of the error the `NAME_KEY` of your Controller.

Example:
```java
Optional<TaskModel> optionalTask = this.taskRepository.getTaskByTaskId(taskId);
if (!optionalTask.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);
```

### Repositories

Repositories are the imaginary database. Every Repository has to extend `JpaRepository`. 
You can write your own methods in this interface and they will be transformed to *SQL-Queries* by *JPA*.

#### Optional

If you just want to access one object you have to use `Optional`, because it is easy to check if your Result is Present.
To access one object the method has to start with `find..`.

Example:
```java
Optional<TaskModel> getTaskByTaskId(int taskId);
```

#### Lists

The Repository can return Lists. You just have to create a method which starts with `findAll..`. 
The returning list can't be null, but empty.

Example:
```java
List<TaskModel> findAllByAuthorId(UUID authorId);
```

#### Foreign Keys

If you have to use foreign keys in the Repository if you can. But there's a special if you write a query using the
foreign key. If you use a foreign key you have to use the name like the ID in the foreign key but use the Model.

Example: ``TaskModel``
```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int taskId;
```

Example: ``SolutionRepository``
```java
@Repository
public interface SolutionRepository extends JpaRepository<SolutionModel, Integer> {

	Optional<SolutionModel> findSolutionModelBybTaskId(TaskModel taskId);
```

So in the query and the variable is named ``taskId``, but actually there is a `TaskModel` handed over.

#### Particularities

Every Repository needs the ``@Repository`` annotation.

Example:
```java
@Repository
public interface TaskRepository extends JpaRepository<TaskModel, Integer> {
```

### Model

Models are the entries in the associated repository.

#### Foreign Keys

As same as in the repository it is necessary to use foreign keys. You can reference foreign keys by using the
specific *Model* as field declaration.

But if you use foreign keys you have to tag them with `@OneToOne`, `@ManyToOne` or `@ManyToMany`.
These annotations tell hibernate in which relation they are.

Example:
```java
public class TaskModel {

	...

	@ManyToOne
	private LectureModel lectureId;
```

`@ManyToOne` because **many** *Tasks* can have **one** *Lecture* as a foreign key.

Another Example:
````java
public class SolutionModel {

	...

	@OneToOne
	private TaskModel taskId;
````

``@OneToOne`` because **one** *Solution* can have only **one** *Task* as a foreign key.


#### Particularities

##### Annotations

Every Model has to be annotated with a bunch of annotations:
````java
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
````

##### Conversions

If you have to convert a Model in e.g. a [DTO](#dtos) you are free to add a method in the model
 class so that the code stays clean. Please place them at the bottom of the file and give the methods a clear name.
 
Example:
````java
public class SimpleEquationSolutionModel {

	...

	public CreateSimpleEquationSolutionResponseDTOModel toCreateResponseDTOModel() {
		return new CreateSimpleEquationSolutionResponseDTOModel(this.simpleEquationSolutionId, this.step);
	}
}
````


## Checkstyle

Checkstyle is a maven plugin which detects any code style violations. You have to fix them because the are necessary
to merge into experimental.

If you execute maven install checkstyle will be triggered automatically. The errors describe pretty good where your 
error is thrown and why.

---

#### [Back](./README.md) 