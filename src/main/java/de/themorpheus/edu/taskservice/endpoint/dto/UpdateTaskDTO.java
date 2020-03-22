package de.themorpheus.edu.taskservice.endpoint.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTaskDTO {

    private String task;
    private int necessaryPoints;

    private String taskTypeDisplayName;
    private String subjectDisplayName;
    private String moduleDisplayName;
    private String lectureDisplayName;
    private String difficultyDisplayName;

}
