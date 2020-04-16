package de.themorpheus.edu.taskservice.controller.solution;

import de.themorpheus.edu.taskservice.database.model.solution.ImageSolutionModel;
import de.themorpheus.edu.taskservice.database.model.solution.SolutionModel;
import de.themorpheus.edu.taskservice.database.model.solution.UserImageSolutionModel;
import de.themorpheus.edu.taskservice.database.repository.solution.ImageSolutionRepository;
import de.themorpheus.edu.taskservice.database.repository.solution.UserImageSolutionRepository;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.CheckImageSolutionRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.CreateImageSolutionRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.UpdateImageSolutionRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.solution.GetImageSolutionResponseDTO;
import de.themorpheus.edu.taskservice.pubsub.dto.UserSentImageSolutionDTO;
import de.themorpheus.edu.taskservice.pubsub.pub.MessagePublisher;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static de.themorpheus.edu.taskservice.util.Constants.Solution.Image.NAME_KEY;

@Component
public class ImageSolutionController implements Solution {

	@Autowired private ImageSolutionRepository imageSolutionRepository;
	@Autowired private UserImageSolutionRepository userImageSolutionRepository;

	@Autowired private SolutionController solutionController;

	@Autowired private MessagePublisher messagePublisher;

	public ControllerResult<ImageSolutionModel> createImageSolution(CreateImageSolutionRequestDTO dto) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getOrCreateSolution(dto.getTaskId(), NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);
		if (this.imageSolutionRepository.existsBySolutionId(solutionResult.getResult()))
			return ControllerResult.of(Error.ALREADY_EXISTS, NAME_KEY);

		return ControllerResult.of(this.imageSolutionRepository.save(
				new ImageSolutionModel(-1, solutionResult.getResult(), dto.getUrl())
				)
		);
	}

	public ControllerResult<GetImageSolutionResponseDTO> checkImageSolution(CheckImageSolutionRequestDTO dto) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(dto.getTaskId(), NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		Optional<ImageSolutionModel> optionalImageSolution = this.imageSolutionRepository
				.findBySolutionId(solutionResult.getResult());
		if (!optionalImageSolution.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		userImageSolutionRepository.save(new UserImageSolutionModel(
				-1,
				optionalImageSolution.get(),
				dto.getUrl(),
				UUID.randomUUID()
				)
		);

		this.messagePublisher.publish(new UserSentImageSolutionDTO(
				solutionResult.getResult().getTaskId().getAuthorId(),
				UUID.randomUUID(),
				dto.getUrl()
				)
		);

		return ControllerResult.of(new GetImageSolutionResponseDTO(optionalImageSolution.get().getUrl()));
	}

	public ControllerResult<ImageSolutionModel> updateImageSolution(UpdateImageSolutionRequestDTO dto) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(dto.getTaskId(), NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		Optional<ImageSolutionModel> optionalImageSolution = this.imageSolutionRepository
				.findBySolutionId(solutionResult.getResult());
		if (!optionalImageSolution.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		ImageSolutionModel imageSolution = optionalImageSolution.get();
		imageSolution.setUrl(dto.getUrl());
		return ControllerResult.of(this.imageSolutionRepository.save(imageSolution));
	}

	public ControllerResult<ImageSolutionModel> deleteImageSolution(int taskId) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(taskId, NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		if (!this.imageSolutionRepository.existsBySolutionId(solutionResult.getResult()))
			return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		this.imageSolutionRepository.deleteBySolutionId(solutionResult.getResult());
		return ControllerResult.empty();
	}

	@Override
	public void deleteAll(int taskId) {
		this.deleteImageSolution(taskId);
	}

	@Override
	public void deleteSolutionIdIfDatabaseIsEmpty(SolutionModel solutionId) {
		if (!this.imageSolutionRepository.existsBySolutionId(solutionId))
			this.solutionController.deleteSolution(solutionId);
	}

}
