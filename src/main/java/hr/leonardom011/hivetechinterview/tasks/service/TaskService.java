package hr.leonardom011.hivetechinterview.tasks.service;

import hr.leonardom011.hivetechinterview.tasks.model.response.TaskResponse;
import org.springframework.data.domain.Page;

public interface TaskService {

    Page<TaskResponse> getAllTasks();

}
