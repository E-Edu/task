package de.themorpheus.edu.taskservice.controller.solution;

import de.themorpheus.edu.taskservice.database.model.solution.SolutionModel;
import de.themorpheus.edu.taskservice.database.model.solution.FreestyleSolutionModel;
import de.themorpheus.edu.taskservice.database.repository.solution.SolutionFreestyleRepository;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.freestyle.get.CheckFreestyleSolutionDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.freestyle.get.CreateFreestyleSolutionDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.freestyle.get.UpdateFreestyleSolutionDTO;
import de.themorpheus.edu.taskservice.endpoint.dto.solution.freestyle.ret.GetFreestyleSolutionDTO;
import de.themorpheus.edu.taskservice.util.ControllerResult;
import de.themorpheus.edu.taskservice.util.Error;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FreestyleSolutionController {

    private static final String NAME_KEY = "freestyle_solution";

    @Autowired private SolutionFreestyleRepository solutionFreestyleRepository;

    @Autowired private SolutionController solutionController;

    public ControllerResult<FreestyleSolutionModel> createSolutionFreestyle(CreateFreestyleSolutionDTO dto) {
        ControllerResult<SolutionModel> optionalSolution = this.solutionController.getOrCreateSolution(dto.getTaskId(), NAME_KEY);
        if (optionalSolution.isResultNotPresent()) return ControllerResult.ret(optionalSolution);

        if (this.solutionFreestyleRepository.existsById(dto.getTaskId())) return ControllerResult.of(Error.ALREADY_EXISTS, NAME_KEY);

        return ControllerResult.of(this.solutionFreestyleRepository.save(new FreestyleSolutionModel(-1, dto.getSolution())));
    }

    public ControllerResult<FreestyleSolutionModel> checkSolutionFreestyle(CheckFreestyleSolutionDTO dto) {
        ControllerResult<SolutionModel> optionalSolution = this.solutionController.getSolution(dto.getTaskId(), NAME_KEY);
        if (optionalSolution.isResultNotPresent()) return ControllerResult.ret(optionalSolution);

        Optional<FreestyleSolutionModel> freestyleSolutionModel = this.solutionFreestyleRepository.findById(
                optionalSolution.getResult().getSolutionId());
        if (!freestyleSolutionModel.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

        if (freestyleSolutionModel.get().getSolution().equalsIgnoreCase(dto.getSolution())) return ControllerResult.empty();
        else return ControllerResult.of(Error.WRONG_ANSWER, NAME_KEY);
    }

    public ControllerResult<FreestyleSolutionModel> updateSolutionFreestyle(UpdateFreestyleSolutionDTO dto) {
        ControllerResult<SolutionModel> optionalSolution = this.solutionController.getSolution(dto.getTaskId(), NAME_KEY);
        if (optionalSolution.isResultNotPresent()) return ControllerResult.ret(optionalSolution);

        if (!this.solutionFreestyleRepository.existsById(dto.getTaskId())) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

        Optional<FreestyleSolutionModel> optionalFreestyleSolutionModel = this.solutionFreestyleRepository.findById(optionalSolution.getResult().getSolutionId());
        if (!optionalFreestyleSolutionModel.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

        FreestyleSolutionModel freestyleSolutionModel = optionalFreestyleSolutionModel.get();
        freestyleSolutionModel.setSolution(dto.getSolution());
        return ControllerResult.of(this.solutionFreestyleRepository.save(freestyleSolutionModel));
    }

    public ControllerResult<FreestyleSolutionModel> deleteSolutionFreestyle(int taskId) {
        ControllerResult<SolutionModel> optionalSolution = this.solutionController.getSolution(taskId, NAME_KEY);
        if (optionalSolution.isResultNotPresent()) return ControllerResult.ret(optionalSolution);

        this.solutionFreestyleRepository.deleteById(optionalSolution.getResult().getSolutionId());
        return ControllerResult.empty();
    }

    public ControllerResult<GetFreestyleSolutionDTO> getSolutionFreestyle(int taskId) {
        ControllerResult<SolutionModel> optionalSolution = this.solutionController.getSolution(taskId, NAME_KEY);
        if (optionalSolution.isResultNotPresent()) return ControllerResult.ret(optionalSolution);

        Optional<FreestyleSolutionModel> freestyleSolutionModel = this.solutionFreestyleRepository.findById(
                optionalSolution.getResult().getSolutionId());
        if (!freestyleSolutionModel.isPresent()) return ControllerResult.of(Error.NOT_FOUND, NAME_KEY);

        List<Character> characters = new ArrayList<>(freestyleSolutionModel.get().getSolution().length());
        for (char c : freestyleSolutionModel.get().getSolution().toCharArray()) characters.add(c);

        StringBuilder output = new StringBuilder(freestyleSolutionModel.get().getSolution().length());
        while (characters.size() != 0) {
            int randPicker = (int) (Math.random() * characters.size());
            output.append(characters.remove(randPicker));
        }

        return ControllerResult.of(new GetFreestyleSolutionDTO(output.toString()));
    }

}
