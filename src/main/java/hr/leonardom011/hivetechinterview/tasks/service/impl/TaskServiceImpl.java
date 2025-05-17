package hr.leonardom011.hivetechinterview.tasks.service.impl;

import hr.leonardom011.hivetechinterview.constant.TaskStatus;
import hr.leonardom011.hivetechinterview.tasks.model.entity.TaskEntity;
import hr.leonardom011.hivetechinterview.tasks.model.mapper.TaskMapper;
import hr.leonardom011.hivetechinterview.tasks.model.request.TaskCreateRequest;
import hr.leonardom011.hivetechinterview.tasks.model.response.TaskResponse;
import hr.leonardom011.hivetechinterview.tasks.repository.TaskRepository;
import hr.leonardom011.hivetechinterview.tasks.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public TaskResponse createTask(TaskCreateRequest taskCreateRequest) {
        log.info("Creating a new task");
        TaskEntity entity = taskMapper.mapToTaskEntity(taskCreateRequest);
        TaskEntity savedEntity = taskRepository.save(entity);
        log.info("Task created successfully");
        return taskMapper.mapToResponse(savedEntity);
    }
}
