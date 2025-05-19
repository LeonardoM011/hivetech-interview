package hr.leonardom011.hivetechinterview.tasks.model.request;

import hr.leonardom011.hivetechinterview.constant.TaskPriority;
import hr.leonardom011.hivetechinterview.constant.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskCreateRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Status is required")
    private TaskStatus status;

    @NotNull(message = "Priority is required")
    private TaskPriority priority;
}
