package dev.galiev.regitbackend.controller;

import dev.galiev.regitbackend.model.Project;
import dev.galiev.regitbackend.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080/")
@RestController
@RequestMapping("/api/v1/tasks")
public class ProjectController {

    @Autowired
    private ProjectRepository projectsRepository;

    @GetMapping("/")
    public ResponseEntity<List<Project>> getTasks() {
        return new ResponseEntity<>(projectsRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getTaskById(@PathVariable long id) {
        return projectsRepository.findById(id)
                .map(task -> new ResponseEntity<>(task, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
