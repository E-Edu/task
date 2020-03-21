package de.themorpheus.edu.taskservice.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

@RestController
public class Task {

    @GetMapping(value = "/task", produces = MediaType.APPLICATION_JSON_VALUE)
    public String task(Model model) {
        return null;
    }

    public String createTask(Model model) {
        return null;
    }

    public String getTask(Model model) {
        return null;
    }

    public String getAllTask(Model model) {
        return null;
    }

}
