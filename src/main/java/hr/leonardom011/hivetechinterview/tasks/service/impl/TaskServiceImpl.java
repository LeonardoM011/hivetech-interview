package hr.leonardom011.hivetechinterview.tasks.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import hr.leonardom011.hivetechinterview.constant.TaskStatus;
import hr.leonardom011.hivetechinterview.exception.KanbanException;
import hr.leonardom011.hivetechinterview.tasks.model.entity.TaskEntity;
import hr.leonardom011.hivetechinterview.tasks.model.mapper.TaskMapper;
import hr.leonardom011.hivetechinterview.tasks.model.request.TaskCreateRequest;
import hr.leonardom011.hivetechinterview.tasks.model.response.TaskResponse;
import hr.leonardom011.hivetechinterview.tasks.repository.TaskRepository;
import hr.leonardom011.hivetechinterview.tasks.service.TaskService;
import hr.leonardom011.hivetechinterview.websocket.model.response.TaskChangedResponse;
import hr.leonardom011.hivetechinterview.websocket.service.WebSocketEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;

import static hr.leonardom011.hivetechinterview.constant.ExceptionConstant.TASK_NOT_FOUND;
import static hr.leonardom011.hivetechinterview.constant.ExceptionConstant.UNEXPECTED_EXCEPTION;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final ObjectMapper objectMapper;
    private final WebSocketEventService webSocketEventService;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper, ObjectMapper objectMapper, WebSocketEventService webSocketEventService) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.objectMapper = objectMapper;
        this.webSocketEventService = webSocketEventService;
    }

    @Override
    public Page<TaskResponse> getAllTasks(TaskStatus taskStatus, Pageable pageable) {
        //TaskStatus status = taskStatus.isBlank() ? TaskStatus.valueOf(taskStatus.toUpperCase()) : null;
        Page<TaskEntity> entities = taskRepository.findByIsDeletedFalseAndStatus(taskStatus, pageable);
        return entities.map(taskMapper::mapToResponse);
    }

    @Override
    public TaskResponse getTask(Long taskId) {
        log.info("Fetching task with ID {}", taskId);
        TaskEntity taskEntity = taskRepository.findByIdAndIsDeletedFalse(taskId)
                .orElseThrow(() -> new KanbanException(HttpStatus.NOT_FOUND, MessageFormat.format(TASK_NOT_FOUND, taskId)));
        return taskMapper.mapToResponse(taskEntity);
    }

    @Override
    @Transactional
    public TaskResponse createTask(TaskCreateRequest taskCreateRequest) {
        log.info("Creating a new task");
        TaskEntity entity = taskMapper.mapToTaskEntity(taskCreateRequest);
        TaskEntity savedEntity = taskRepository.save(entity);
        log.info("Task created successfully");
        webSocketEventService.notifyTaskChanged(new TaskChangedResponse(savedEntity.getId()));
        return taskMapper.mapToResponse(savedEntity);
    }

    @Override
    @Transactional
    public TaskResponse updateTask(Long taskId, TaskCreateRequest taskUpdateRequest) {
        log.info("Updating task with ID {}", taskId);
        TaskEntity taskEntity = taskRepository.findByIdAndIsDeletedFalse(taskId)
                .orElseThrow(() -> new KanbanException(HttpStatus.NOT_FOUND, MessageFormat.format(TASK_NOT_FOUND, taskId)));

        taskMapper.mapForUpdate(taskEntity, taskUpdateRequest);

        TaskEntity updatedEntity = taskRepository.save(taskEntity);
        log.info("Task with ID {} updated successfully", taskId);
        webSocketEventService.notifyTaskChanged(new TaskChangedResponse(taskId));
        return taskMapper.mapToResponse(updatedEntity);
    }

    @Override
    @Transactional
    public TaskResponse patchTask(Long taskId, JsonPatch jsonPatch) {
        log.info("Patching task with ID {}", taskId);
        TaskEntity taskEntity = taskRepository.findByIdAndIsDeletedFalse(taskId)
                .orElseThrow(() -> new KanbanException(HttpStatus.NOT_FOUND, MessageFormat.format(TASK_NOT_FOUND, taskId)));

        TaskEntity patchedTask = applyPatchToTask(jsonPatch, taskEntity);

        taskRepository.updateTitleAndDescriptionAndStatusAndPriorityByIsDeletedFalse(
                patchedTask.getTitle(),
                patchedTask.getDescription(),
                patchedTask.getStatus(),
                patchedTask.getPriority());
        log.info("Task with ID {} patched successfully", taskId);
        webSocketEventService.notifyTaskChanged(new TaskChangedResponse(taskId));
        return taskMapper.mapToResponse(patchedTask);
    }

    @Override
    public void deleteTask(Long taskId) {
        log.info("Deleting task with ID {}", taskId);
        TaskEntity taskEntity = taskRepository.findByIdAndIsDeletedFalse(taskId)
                .orElseThrow(() -> new KanbanException(HttpStatus.NOT_FOUND, MessageFormat.format(TASK_NOT_FOUND, taskId)));
        taskEntity.setDeleted(true);
        taskRepository.save(taskEntity);
        log.info("Task with ID {} marked as deleted", taskId);
        webSocketEventService.notifyTaskChanged(new TaskChangedResponse(taskId));
    }

    private TaskEntity applyPatchToTask(JsonPatch jsonPatch, TaskEntity taskEntity) {
        try {
            JsonNode patchedTaskNode = jsonPatch.apply(objectMapper.convertValue(taskEntity, JsonNode.class));
            return objectMapper.treeToValue(patchedTaskNode, TaskEntity.class);
        } catch (JsonPatchException | JsonProcessingException e) {
            log.error("Error applying JSON patch", e);
            throw new KanbanException(HttpStatus.INTERNAL_SERVER_ERROR, UNEXPECTED_EXCEPTION);
        }
    }
}
