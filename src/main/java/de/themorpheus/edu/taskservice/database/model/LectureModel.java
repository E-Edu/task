package de.themorpheus.edu.taskservice.database.model;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "module")
public class LectureModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int moduleId;

    @NotNull
    private String displayName;

    public LectureModel() {
    }

    public LectureModel(String displayName) {
        this.displayName = displayName;
    }

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
