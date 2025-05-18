package hr.leonardom011.hivetechinterview.tasks.model.mapper;

import hr.leonardom011.hivetechinterview.tasks.model.entity.TaskEntity;
import hr.leonardom011.hivetechinterview.tasks.model.request.TaskCreateRequest;
import hr.leonardom011.hivetechinterview.tasks.model.response.TaskResponse;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public TaskResponse mapToResponse(TaskEntity taskEntity) {
        TaskResponse response = new TaskResponse();
        response.setId(taskEntity.getId());
        response.setTitle(taskEntity.getTitle());
        response.setDescription(taskEntity.getDescription());
        response.setStatus(taskEntity.getStatus());
        response.setPriority(taskEntity.getPriority());
        return response;
    }

    public TaskEntity mapToTaskEntity(TaskCreateRequest taskCreateRequest) {
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setTitle(taskCreateRequest.getTitle());
        taskEntity.setDescription(taskCreateRequest.getDescription());
        taskEntity.setStatus(taskCreateRequest.getStatus());
        taskEntity.setPriority(taskCreateRequest.getPriority());
        taskEntity.setDeleted(false);
        return taskEntity;
    }

    public TaskEntity mapForUpdate(TaskEntity taskEntity, TaskCreateRequest taskUpdateRequest) {
        taskEntity.setTitle(taskUpdateRequest.getTitle());
        taskEntity.setDescription(taskUpdateRequest.getDescription());
        taskEntity.setStatus(taskUpdateRequest.getStatus());
        taskEntity.setPriority(taskUpdateRequest.getPriority());
        return taskEntity;
    }
}
