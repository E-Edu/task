package de.themorpheus.edu.taskservice.controller.solution;

import de.themorpheus.edu.taskservice.controller.user.UserDataHandler;
import de.themorpheus.edu.taskservice.database.model.solution.ImageSolutionModel;
import de.themorpheus.edu.taskservice.database.model.solution.SolutionModel;
import de.themorpheus.edu.taskservice.database.model.solution.UserImageSolutionModel;
import de.themorpheus.edu.taskservice.database.repository.solution.ImageSolutionRepository;
import de.themorpheus.edu.taskservice.database.repository.solution.UserImageSolutionRepository;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.CheckImageSolutionRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.CreateImageSolutionRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.UpdateImageSolutionRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.solution.CheckImageSolutionResponseDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.solution.CreateImageSolutionResponseDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.solution.GetAllUserImageSolutionResponseDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.solution.GetAllUserImageSolutionsResponseDTOModel;
import de.themorpheus.edu.taskservice.endpoint.dto.response.solution.UpdateImageSolutionResponseDTO;
import de.themorpheus.edu.taskservice.pubsub.dto.UserSentImageSolutionDTO;
import de.themorpheus.edu.taskservice.pubsub.pub.MessagePublisher;
import de.themorpheus.edu.taskservice.util.Constants;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static de.themorpheus.edu.taskservice.util.Constants.Solution.Image.NAME_KEY;

@Component
public class ImageSolutionController implements Solution, UserDataHandler {

	@Autowired private ImageSolutionRepository imageSolutionRepository;
	@Autowired private UserImageSolutionRepository userImageSolutionRepository;

	@Autowired private SolutionController solutionController;

	@Autowired private MessagePublisher messagePublisher;

	public ControllerResult<CreateImageSolutionResponseDTO> createImageSolution(CreateImageSolutionRequestDTO dto) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getOrCreateSolution(dto.getTaskId(), NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		if (this.imageSolutionRepository.existsBySolutionId(solutionResult.getResult()))
			return ControllerResult.of(Error.ALREADY_EXISTS, NAME_KEY);

		ImageSolutionModel imageSolution = this.imageSolutionRepository.save(
				new ImageSolutionModel(-1, solutionResult.getResult(), dto.getUrl()));

		return ControllerResult.of(
				new CreateImageSolutionResponseDTO(imageSolution.getImageSolutionId(), imageSolution.getUrl()));
	}

	public ControllerResult<CheckImageSolutionResponseDTO> checkImageSolution(CheckImageSolutionRequestDTO dto) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(dto.getTaskId(), NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		Optional<ImageSolutionModel> optionalImageSolution = this.imageSolutionRepository
				.findBySolutionId(solutionResult.getResult());
		if (!optionalImageSolution.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		userImageSolutionRepository.save(new UserImageSolutionModel(
				-1,
				dto.getUrl(),
				optionalImageSolution.get(),
				Constants.UserId.TEST_UUID
			)
		);

		this.messagePublisher.publish(new UserSentImageSolutionDTO(
				solutionResult.getResult().getTaskId().getTaskId(),
				solutionResult.getResult().getTaskId().getAuthorId(),
				Constants.UserId.TEST_UUID,
				dto.getUrl()
			)
		);

		return ControllerResult.of(new CheckImageSolutionResponseDTO(optionalImageSolution.get().getUrl()));
	}

	public ControllerResult<UpdateImageSolutionResponseDTO> updateImageSolution(UpdateImageSolutionRequestDTO dto) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(dto.getTaskId(), NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		Optional<ImageSolutionModel> optionalImageSolution = this.imageSolutionRepository
				.findBySolutionId(solutionResult.getResult());
		if (!optionalImageSolution.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		ImageSolutionModel imageSolution = optionalImageSolution.get();
		imageSolution.setUrl(dto.getUrl());
		imageSolution = this.imageSolutionRepository.save(imageSolution);

		return ControllerResult.of(
				new UpdateImageSolutionResponseDTO(imageSolution.getImageSolutionId(), imageSolution.getUrl()));
	}

	public ControllerResult<GetAllUserImageSolutionResponseDTO> getAllUserImageSolution(int taskId) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(taskId, NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		Optional<ImageSolutionModel> optionalImageSolution = this.imageSolutionRepository
				.findBySolutionId(solutionResult.getResult());
		if (!optionalImageSolution.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		ImageSolutionModel imageSolution = optionalImageSolution.get();
		List<GetAllUserImageSolutionsResponseDTOModel> userImageSolutions = new ArrayList<>();
		this.userImageSolutionRepository.findAllByImageSolutionId(imageSolution).forEach(
				userImageSolution -> userImageSolutions.add(userImageSolution.toGetAllResponseDTOModel()));
		if (userImageSolutions.isEmpty()) return ControllerResult
			.of(Error.NOT_FOUND, Constants.Solution.Image.UserSolutions.NAME_KEY);

		return ControllerResult.of(new GetAllUserImageSolutionResponseDTO(userImageSolutions));
	}

	@Transactional
	public ControllerResult<ImageSolutionModel> deleteImageSolution(int taskId) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(taskId, NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		if (!this.imageSolutionRepository.existsBySolutionId(solutionResult.getResult()))
			return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		this.imageSolutionRepository.deleteBySolutionId(solutionResult.getResult());
		this.deleteSolutionIdIfDatabaseIsEmpty(solutionResult.getResult());
		return ControllerResult.empty();
	}

	@Override
	public void deleteAll(SolutionModel solution) {
		this.imageSolutionRepository.deleteBySolutionId(solution);
	}

	@Override
	public void deleteSolutionIdIfDatabaseIsEmpty(SolutionModel solutionId) {
		if (!this.imageSolutionRepository.existsBySolutionId(solutionId))
			this.solutionController.deleteSolution(solutionId);
	}

	@Override
	public void deleteOrMaskUserData(UUID userId) {
		this.userImageSolutionRepository.deleteAllByUserId(userId);
	}

	@Override
	public ControllerResult<Object> getUserData(UUID userId) {
		List<GetAllUserImageSolutionsResponseDTOModel> responseDTOs = new ArrayList<>();
		this.userImageSolutionRepository.findAllByUserId(userId)
				.forEach(userFreestyleSolution -> responseDTOs.add(userFreestyleSolution.toGetAllResponseDTOModel()));
		if (responseDTOs.isEmpty())
			return ControllerResult.of(Error.NOT_FOUND, Constants.Solution.Image.UserSolutions.NAME_KEY);

		return ControllerResult.of(responseDTOs);
	}

}
