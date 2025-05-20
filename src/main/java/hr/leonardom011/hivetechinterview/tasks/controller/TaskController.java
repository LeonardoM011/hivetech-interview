package hr.leonardom011.hivetechinterview.tasks.controller;

import com.github.fge.jsonpatch.JsonPatch;
import hr.leonardom011.hivetechinterview.annotations.ApiPageable;
import hr.leonardom011.hivetechinterview.constant.TaskStatus;
import hr.leonardom011.hivetechinterview.tasks.model.request.TaskCreateRequest;
import hr.leonardom011.hivetechinterview.tasks.model.response.TaskResponse;
import hr.leonardom011.hivetechinterview.tasks.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@Slf4j
@Validated
@CrossOrigin
@RequestMapping("api/tasks")
@Secured("ROLE_USER")
@SecurityRequirement(name = "bearerAuth")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get tasks", description = "Endpoint for getting all tasks")
    @ApiPageable
    public ResponseEntity<Page<TaskResponse>> getTasks(@RequestParam(required = false) TaskStatus status,
                                                       @Parameter(hidden = true) @PageableDefault Pageable pageable) {
        log.info("GET /api/tasks started");
        Page<TaskResponse> response = taskService.getAllTasks(status, pageable);
        log.info("GET /api/tasks finished");
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get task by ID", description = "Endpoint for retrieving a task by its ID")
    public ResponseEntity<?> getTask(@PathVariable Long taskId) {
        log.info("GET /api/tasks/{} started", taskId);
        TaskResponse response = taskService.getTask(taskId);
        log.info("GET /api/tasks/{} finished", taskId);
        return ResponseEntity.ok(response);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a new task", description = "Endpoint for creating a new task")
    public ResponseEntity<TaskResponse> createTask(@RequestBody @Validated TaskCreateRequest taskCreateRequest) {
        log.info("POST /api/tasks started");
        TaskResponse response = taskService.createTask(taskCreateRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{taskId}")
                .buildAndExpand(response.getId())
                .toUri();
        log.info("POST /api/tasks finished");
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping(value = "/{taskId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update task", description = "Endpoint for updating a task by its ID")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable Long taskId,
                                                   @RequestBody @Validated TaskCreateRequest taskUpdateRequest) {
        log.info("PUT /api/tasks/{} started", taskId);
        TaskResponse response = taskService.updateTask(taskId, taskUpdateRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{taskId}")
                .buildAndExpand(response.getId())
                .toUri();
        log.info("PUT /api/tasks/{} finished", taskId);
        return ResponseEntity.created(location).body(response);
    }

    @PatchMapping(value = "/{taskId}", consumes = "application/json-patch+json", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Patch task", description = "Endpoint for partially updating a task by its ID")
    public ResponseEntity<TaskResponse> patchTask(@PathVariable Long taskId, @RequestBody JsonPatch jsonPatch) {
        log.info("PATCH /api/tasks/{} started", taskId);
        TaskResponse response = taskService.patchTask(taskId, jsonPatch);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{taskId}")
                .buildAndExpand(response.getId())
                .toUri();
        log.info("PATCH /api/tasks/{} finished", taskId);
        return ResponseEntity.created(location).body(response);
    }

    @DeleteMapping(value = "/{taskId}")
    @Operation(summary = "Delete task", description = "Endpoint for deleting a task by its ID")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId) {
        log.info("DELETE /api/tasks/{} started", taskId);
        taskService.deleteTask(taskId);
        log.info("DELETE /api/tasks/{} finished", taskId);
        return ResponseEntity.noContent().build();
    }

}
