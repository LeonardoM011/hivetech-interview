package hr.leonardom011.hivetechinterview.tasks.service;

import com.github.fge.jsonpatch.JsonPatch;
import hr.leonardom011.hivetechinterview.constant.TaskStatus;
import hr.leonardom011.hivetechinterview.tasks.model.request.TaskCreateRequest;
import hr.leonardom011.hivetechinterview.tasks.model.response.TaskResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {

    Page<TaskResponse> getAllTasks(TaskStatus taskStatus, Pageable pageable);

    TaskResponse getTask(Long taskId);

    TaskResponse updateTask(Long taskId, TaskCreateRequest taskUpdateRequest);

    TaskResponse patchTask(Long taskId, JsonPatch jsonPatch);

    TaskResponse createTask(TaskCreateRequest taskCreateRequest);

    void deleteTask(Long taskId);
}
