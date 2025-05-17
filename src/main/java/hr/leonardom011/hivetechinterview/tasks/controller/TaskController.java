package hr.leonardom011.hivetechinterview.tasks.controller;

import hr.leonardom011.hivetechinterview.annotations.ApiPageable;
import hr.leonardom011.hivetechinterview.tasks.model.request.TaskCreateRequest;
import hr.leonardom011.hivetechinterview.tasks.model.response.TaskResponse;
import hr.leonardom011.hivetechinterview.tasks.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@Validated
@CrossOrigin
@RequestMapping("api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get tasks", description = "Endpoint for getting all tasks")
    @ApiPageable
    public ResponseEntity<Page<TaskResponse>> getTasks(@RequestParam(required = false) String status,
                                                       @Parameter(hidden = true) @PageableDefault Pageable pageable) {
        log.info("GET /api/tasks started");
        Page<TaskResponse> response = taskService.getAllTasks(status, pageable);
        log.info("GET /api/tasks finished");
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTask(Integer taskId) {
        return null;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a new task", description = "Endpoint for creating a new task")
    public ResponseEntity<TaskResponse> createTask(@RequestBody @Validated TaskCreateRequest taskCreateRequest) {
        log.info("POST /api/tasks started");
        TaskResponse response = taskService.createTask(taskCreateRequest);
        log.info("POST /api/tasks finished");
        return ResponseEntity.ok(response);
    }

    @PutMapping(value = "/{taskId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation
    public ResponseEntity<?> updateTask(Integer taskId) {
        return null;
    }

    @PatchMapping(value = "/{taskId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation
    public ResponseEntity<?> pathTask(Integer taskId) {
        return null;
    }

    @DeleteMapping(value = "/{taskId}")
    @Operation
    public ResponseEntity<?> deleteTask(Integer taskId) {
        return null;
    }

}
