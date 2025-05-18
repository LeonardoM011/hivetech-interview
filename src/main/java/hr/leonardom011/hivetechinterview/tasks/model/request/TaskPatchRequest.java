package hr.leonardom011.hivetechinterview.tasks.model.request;

import hr.leonardom011.hivetechinterview.constant.TaskPriority;
import hr.leonardom011.hivetechinterview.constant.TaskStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskPatchRequest {
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
}
