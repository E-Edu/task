package de.themorpheus.edu.taskservice.controller.solution;

import de.themorpheus.edu.taskservice.database.model.solution.SolutionModel;
import de.themorpheus.edu.taskservice.database.model.solution.TopicConnectionSolutionModel;
import de.themorpheus.edu.taskservice.database.repository.solution.TopicConnectionSolutionRepository;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.CheckTopicConnectionSolutionRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.CheckTopicConnectionSolutionRequestDTOModel;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.CreateTopicConnectionSolutionRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.solution.CheckTopicConnectionSolutionResponseDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.solution.CheckTopicConnectionSolutionResponseDTOModel;
import de.themorpheus.edu.taskservice.endpoint.dto.response.solution.CreateTopicConnectionSolutionResponseDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.solution.GetAllTopicConnectionSolutionsResponseDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.solution.GetAllTopicConnectionSolutionsResponseDTOModel;
import de.themorpheus.edu.taskservice.endpoint.dto.response.solution.GetTopicConnectionSolutionResponseDTO;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static de.themorpheus.edu.taskservice.util.Constants.Solution.TopicConnection.NAME_KEY;

@Component
public class TopicConnectionSolutionController implements Solution {

	@Autowired private TopicConnectionSolutionRepository topicConnectionSolutionRepository;

	@Autowired private SolutionController solutionController;

	public ControllerResult<CreateTopicConnectionSolutionResponseDTO> createTopicConnectionSolution(CreateTopicConnectionSolutionRequestDTO dto) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getOrCreateSolution(dto.getTaskId(), NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		if (dto.getPointB() == null) dto.setPointB(dto.getPointA());
		if (this.topicConnectionSolutionRepository.existsBySolutionIdAndPointAAndPointB(solutionResult.getResult(), dto.getPointA(), dto.getPointB()))
			return ControllerResult.of(Error.ALREADY_EXISTS, NAME_KEY);

		TopicConnectionSolutionModel topicConnectionSolution = this.topicConnectionSolutionRepository
				.save(new TopicConnectionSolutionModel(
						-1,
						solutionResult.getResult(),
						dto.getPointA(),
						dto.getPointB()
					)
				);

		return ControllerResult.of(new CreateTopicConnectionSolutionResponseDTO(
				topicConnectionSolution.getTopicConnectionSolutionId(),
				topicConnectionSolution.getPointA(),
				topicConnectionSolution.getPointB()
				)
		);
	}

	public ControllerResult<CheckTopicConnectionSolutionResponseDTO> checkTopicConnectionSolution(CheckTopicConnectionSolutionRequestDTO dto) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(dto.getTaskId(), NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		List<CheckTopicConnectionSolutionResponseDTOModel> topicConnectionSolutions = new ArrayList<>();
		this.topicConnectionSolutionRepository.findAllBySolutionId(solutionResult.getResult()).forEach(
				topicConnectionSolutionModel -> topicConnectionSolutions.add(topicConnectionSolutionModel.toCheckResponseDTOModel()));
		if (topicConnectionSolutions.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		CheckTopicConnectionSolutionResponseDTO responseDTO = new CheckTopicConnectionSolutionResponseDTO();
		for (CheckTopicConnectionSolutionResponseDTOModel topicConnectionSolution : topicConnectionSolutions) {
			CheckTopicConnectionSolutionRequestDTOModel requestDTOModel = topicConnectionSolution.toRequestDTOModel();
			if (dto.getConnectedPoints().contains(requestDTOModel)) {
				responseDTO.getCorrect().add(topicConnectionSolution.toResponseDTOModel());
			} else {
				CheckTopicConnectionSolutionResponseDTOModel responseDTOModel = topicConnectionSolution.toResponseDTOModel();
				if (!topicConnectionSolutions.contains(responseDTOModel)) {
					responseDTO.getMissing().add(responseDTOModel);
				} else {
					responseDTO.getWrong().add(responseDTOModel);
				}
			}
			dto.getConnectedPoints().remove(requestDTOModel);
			topicConnectionSolutions.remove(topicConnectionSolution);
		}

		return ControllerResult.of(responseDTO);
	}

	public ControllerResult<GetTopicConnectionSolutionResponseDTO> getTopicConnectionSolution(int taskId) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(taskId, NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		List<TopicConnectionSolutionModel> topicConnectionSolutions = this.topicConnectionSolutionRepository
				.findAllBySolutionId(solutionResult.getResult());
		if (topicConnectionSolutions.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		List<String> points = new ArrayList<>();
		for (TopicConnectionSolutionModel topicConnectionSolution : topicConnectionSolutions) {
			if (!points.contains(topicConnectionSolution.getPointA())) points.add(topicConnectionSolution.getPointA());
			if (topicConnectionSolution.getPointB() != null && !points.contains(topicConnectionSolution.getPointB()))
				points.add(topicConnectionSolution.getPointB());
		}

		return ControllerResult.of(new GetTopicConnectionSolutionResponseDTO(points));
	}

	public ControllerResult<TopicConnectionSolutionModel> deleteTopicConnectionSolution(int topicConnectionSolutionId) {
		Optional<TopicConnectionSolutionModel> topicConnectionSolution = this.
				topicConnectionSolutionRepository.findById(topicConnectionSolutionId);
		if (!topicConnectionSolution.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		this.topicConnectionSolutionRepository.delete(topicConnectionSolution.get());
		this.deleteSolutionIdIfDatabaseIsEmpty(topicConnectionSolution.get().getSolutionId());

		return ControllerResult.empty();
	}

	public ControllerResult<GetAllTopicConnectionSolutionsResponseDTO> getAllTopicConnectionSolutions(int taskId) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(taskId, NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		List<GetAllTopicConnectionSolutionsResponseDTOModel> topicConnectionSolutions = new ArrayList<>();
		this.topicConnectionSolutionRepository.findAllBySolutionId(solutionResult.getResult()).forEach(
				topicConnectionSolutionModel -> topicConnectionSolutions.add(topicConnectionSolutionModel.toGetAllResponseDTOModel()));
		if (topicConnectionSolutions.isEmpty()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		return ControllerResult.of(new GetAllTopicConnectionSolutionsResponseDTO(topicConnectionSolutions));
	}

	@Override
	public void deleteAll(SolutionModel solution) {
		this.topicConnectionSolutionRepository.deleteAllBySolutionId(solution);
	}

	@Override
	public void deleteSolutionIdIfDatabaseIsEmpty(SolutionModel solutionId) {
		if (!this.topicConnectionSolutionRepository.existsBySolutionId(solutionId))
			this.solutionController.deleteSolution(solutionId);
	}
}
