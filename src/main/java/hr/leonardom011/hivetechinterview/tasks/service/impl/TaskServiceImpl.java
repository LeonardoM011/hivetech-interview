package hr.leonardom011.hivetechinterview.tasks.service.impl;

import hr.leonardom011.hivetechinterview.constant.TaskStatus;
import hr.leonardom011.hivetechinterview.exception.KanbanException;
import hr.leonardom011.hivetechinterview.tasks.model.entity.TaskEntity;
import hr.leonardom011.hivetechinterview.tasks.model.mapper.TaskMapper;
import hr.leonardom011.hivetechinterview.tasks.model.request.TaskCreateRequest;
import hr.leonardom011.hivetechinterview.tasks.model.request.TaskPatchRequest;
import hr.leonardom011.hivetechinterview.tasks.model.response.TaskResponse;
import hr.leonardom011.hivetechinterview.tasks.repository.TaskRepository;
import hr.leonardom011.hivetechinterview.tasks.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

import static hr.leonardom011.hivetechinterview.constant.ExceptionConstant.TASK_NOT_FOUND;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    public Page<TaskResponse> getAllTasks(String taskStatus, Pageable pageable) {
        TaskStatus status = taskStatus.isBlank() ? TaskStatus.valueOf(taskStatus.toUpperCase()) : null;
        Page<TaskEntity> entities = taskRepository.findByIsDeletedFalseAndStatus(status, pageable);
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
    public TaskResponse updateTask(Long taskId, TaskCreateRequest taskUpdateRequest) {
        log.info("Updating task with ID {}", taskId);
        TaskEntity taskEntity = taskRepository.findByIdAndIsDeletedFalse(taskId)
                .orElseThrow(() -> new KanbanException(HttpStatus.NOT_FOUND, MessageFormat.format(TASK_NOT_FOUND, taskId)));

        taskMapper.mapForUpdate(taskEntity, taskUpdateRequest);

        TaskEntity updatedEntity = taskRepository.save(taskEntity);
        log.info("Task with ID {} updated successfully", taskId);
        return taskMapper.mapToResponse(updatedEntity);
    }

    @Override
    public TaskResponse patchTask(Long taskId, TaskPatchRequest taskPatchRequest) {
        log.info("Patching task with ID {}", taskId);
        TaskEntity taskEntity = taskRepository.findByIdAndIsDeletedFalse(taskId)
                .orElseThrow(() -> new KanbanException(HttpStatus.NOT_FOUND, MessageFormat.format(TASK_NOT_FOUND, taskId)));

        if (taskPatchRequest.getTitle() != null) {
            taskEntity.setTitle(taskPatchRequest.getTitle());
        }
        if (taskPatchRequest.getDescription() != null) {
            taskEntity.setDescription(taskPatchRequest.getDescription());
        }
        if (taskPatchRequest.getStatus() != null) {
            taskEntity.setStatus(taskPatchRequest.getStatus());
        }
        if (taskPatchRequest.getPriority() != null) {
            taskEntity.setPriority(taskPatchRequest.getPriority());
        }

        TaskEntity patchedEntity = taskRepository.save(taskEntity);
        log.info("Task with ID {} patched successfully", taskId);
        return taskMapper.mapToResponse(patchedEntity);
    }

    @Override
    public TaskResponse createTask(TaskCreateRequest taskCreateRequest) {
        log.info("Creating a new task");
        TaskEntity entity = taskMapper.mapToTaskEntity(taskCreateRequest);
        TaskEntity savedEntity = taskRepository.save(entity);
        log.info("Task created successfully");
        return taskMapper.mapToResponse(savedEntity);
    }

    @Override
    public void deleteTask(Long taskId) {
        log.info("Deleting task with ID {}", taskId);
        TaskEntity taskEntity = taskRepository.findByIdAndIsDeletedFalse(taskId)
                .orElseThrow(() -> new KanbanException(HttpStatus.NOT_FOUND, MessageFormat.format(TASK_NOT_FOUND, taskId)));
        taskEntity.setDeleted(true);
        taskRepository.save(taskEntity);
        log.info("Task with ID {} marked as deleted", taskId);
    }
}
