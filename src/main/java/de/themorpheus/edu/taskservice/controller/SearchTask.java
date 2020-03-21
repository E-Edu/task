package de.themorpheus.edu.taskservice.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;

@RestController
public class SearchTask {

    @GetMapping(value = "/task/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public String searchTask(Model model) {
        return null;
    }

}
