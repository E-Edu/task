package de.themorpheus.edu.taskservice.controller.solution;

import de.themorpheus.edu.taskservice.database.model.solution.ImageSolutionModel;
import de.themorpheus.edu.taskservice.database.model.solution.SolutionModel;
import de.themorpheus.edu.taskservice.database.repository.solution.SolutionImageRepository;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.CheckImageSolutionRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.CreateImageSolutionRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.request.solution.UpdateImageSolutionRequestDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.response.solution.GetImageSolutionResponseDTO;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static de.themorpheus.edu.taskservice.util.Constants.Solution.Image.NAME_KEY;

@Component
public class ImageSolutionController implements Solution {

	@Autowired
	private SolutionImageRepository solutionImageRepository;

	@Autowired
	private SolutionController solutionController;

	public ControllerResult<ImageSolutionModel> createSolutionImage(CreateImageSolutionRequestDTO dto) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getOrCreateSolution(dto.getTaskId(), NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		if (this.solutionImageRepository.existsById(dto.getTaskId()))
			return ControllerResult.of(Error.ALREADY_EXISTS, NAME_KEY);

		return ControllerResult.of(this.solutionImageRepository.save(
				new ImageSolutionModel(-1, solutionResult.getResult(), dto.getSolution())
				)
		);
	}

	public ControllerResult<ImageSolutionModel> checkSolutionImage(CheckImageSolutionRequestDTO dto) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(dto.getTaskId(), NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		Optional<ImageSolutionModel> optionalImageSolution = this.solutionImageRepository.findById(solutionResult.getResult().getSolutionId());
		if (!optionalImageSolution.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		if (!optionalImageSolution.get().getSolution().equalsIgnoreCase(dto.getSolution()))
			return ControllerResult.of(Error.WRONG_ANSWER, NAME_KEY);

		return ControllerResult.empty();
	}

	public ControllerResult<ImageSolutionModel> updateSolutionImage(UpdateImageSolutionRequestDTO dto) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(dto.getTaskId(), NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		Optional<ImageSolutionModel> optionalImageSolution = this.solutionImageRepository
				.findById(solutionResult.getResult().getSolutionId());
		if (!optionalImageSolution.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		ImageSolutionModel imageSolution = optionalImageSolution.get();
		imageSolution.setSolution(dto.getSolution());
		return ControllerResult.of(this.solutionImageRepository.save(imageSolution));
	}

	public ControllerResult<ImageSolutionModel> deleteSolutionImage(int taskId) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(taskId, NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		if (!this.solutionImageRepository.existsById(solutionResult.getResult().getSolutionId()))
			return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		this.solutionImageRepository.deleteById(solutionResult.getResult().getSolutionId());
		return ControllerResult.empty();
	}

	public ControllerResult<GetImageSolutionResponseDTO> getSolutionImage(int taskId) {
		ControllerResult<SolutionModel> solutionResult = this.solutionController.getGenericSolution(taskId, NAME_KEY);
		if (solutionResult.isResultNotPresent()) return ControllerResult.ret(solutionResult);

		Optional<ImageSolutionModel> optionalImageSolution = this.solutionImageRepository.findById(
				solutionResult.getResult().getSolutionId());
		if (!optionalImageSolution.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

		GetImageSolutionResponseDTO dto = new GetImageSolutionResponseDTO(optionalImageSolution.get().getSolution());

		return ControllerResult.of(dto);
	}

	@Override
	public void deleteAll(int taskId) {
		this.deleteSolutionImage(taskId);
	}

}
