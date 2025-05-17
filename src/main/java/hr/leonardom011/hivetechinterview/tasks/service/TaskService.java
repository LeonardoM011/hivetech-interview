package hr.leonardom011.hivetechinterview.tasks.service;

import hr.leonardom011.hivetechinterview.tasks.model.request.TaskCreateRequest;
import hr.leonardom011.hivetechinterview.tasks.model.response.TaskResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {

    Page<TaskResponse> getAllTasks(String taskStatus, Pageable pageable);
    TaskResponse createTask(TaskCreateRequest taskCreateRequest);
}
