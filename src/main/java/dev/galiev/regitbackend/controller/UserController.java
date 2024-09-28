package dev.galiev.regitbackend.controller;

import dev.galiev.regitbackend.model.Project;
import dev.galiev.regitbackend.repository.ProjectRepository;
import dev.galiev.regitbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080/")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public ResponseEntity<List<Project>> userProjects(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> new ResponseEntity<>(user.getProjects(), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/")
    public ResponseEntity<?> addProjects(@RequestBody Project project) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .map(user -> {
                    if (project.getDescription() == null || project.getTitle() == null) {
                        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                    }
                    project.setUser(user);
                    return new ResponseEntity<>(projectRepository.save(project), HttpStatus.CREATED);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/")
    public ResponseEntity<?> updateProject(@RequestBody Project project) {
        Project taskToUpdate = projectRepository.findById(project.getId()).orElse(null);
        if(taskToUpdate != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            return userRepository.findByEmail(email)
                    .map(user -> {
                        if (user.hasPermission(taskToUpdate)) {
                            if (project.getTitle() != null) {
                                taskToUpdate.setTitle(project.getTitle());
                            }
                            if (project.getDescription() != null) {
                                taskToUpdate.setDescription(project.getDescription());
                            }
                            return new ResponseEntity<>(projectRepository.save(taskToUpdate), HttpStatus.OK);
                        } else {
                            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                        }
                    })
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable long id) {
        Project task = projectRepository.findById(id).orElse(null);
        if(task != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            return userRepository.findByEmail(email)
                    .map(user -> {
                        if (user.hasPermission(task)) {
                            projectRepository.delete(task);
                            return new ResponseEntity<>(projectRepository.save(task), HttpStatus.OK);
                        } else {
                            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
                        }
                    })
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
