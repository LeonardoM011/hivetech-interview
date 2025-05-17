package hr.leonardom011.hivetechinterview.tasks.service.impl;

import hr.leonardom011.hivetechinterview.tasks.model.mapper.TaskMapper;
import hr.leonardom011.hivetechinterview.tasks.model.response.TaskResponse;
import hr.leonardom011.hivetechinterview.tasks.repository.TaskRepository;
import hr.leonardom011.hivetechinterview.tasks.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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
    public Page<TaskResponse> getAllTasks() {
        taskRepository.getAllByDeletedIsFalse();
        return null;
    }
}
