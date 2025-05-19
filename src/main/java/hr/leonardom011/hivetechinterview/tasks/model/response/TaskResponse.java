package hr.leonardom011.hivetechinterview.tasks.model.response;

import hr.leonardom011.hivetechinterview.constant.TaskPriority;
import hr.leonardom011.hivetechinterview.constant.TaskStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskResponse {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
}
