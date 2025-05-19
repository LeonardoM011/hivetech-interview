package hr.leonardom011.hivetechinterview.tasks.service;

import com.github.fge.jsonpatch.JsonPatch;
import hr.leonardom011.hivetechinterview.constant.TaskStatus;
import hr.leonardom011.hivetechinterview.tasks.model.request.TaskCreateRequest;
import hr.leonardom011.hivetechinterview.tasks.model.response.TaskResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface TaskService {

    Page<TaskResponse> getAllTasks(TaskStatus taskStatus, Pageable pageable);

    TaskResponse getTask(Long taskId);

    @Transactional
    TaskResponse updateTask(Long taskId, TaskCreateRequest taskUpdateRequest);

    @Transactional
    TaskResponse patchTask(Long taskId, JsonPatch jsonPatch);

    @Transactional
    TaskResponse createTask(TaskCreateRequest taskCreateRequest);

    void deleteTask(Long taskId);
}
