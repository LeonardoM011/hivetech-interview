package hr.leonardom011.hivetechinterview.tasks.model.mapper;

import hr.leonardom011.hivetechinterview.constant.TaskPriority;
import hr.leonardom011.hivetechinterview.constant.TaskStatus;
import hr.leonardom011.hivetechinterview.tasks.model.entity.TaskEntity;
import hr.leonardom011.hivetechinterview.tasks.model.request.TaskCreateRequest;
import hr.leonardom011.hivetechinterview.tasks.model.response.TaskResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskMapperTest {

    private TaskMapper underTest;

    @BeforeEach
    void setUp() {
        underTest = new TaskMapper();
    }

    @Test
    void testMapToResponse() {
        // Given
        TaskEntity taskEntity = new TaskEntity();
        taskEntity.setId(1L);
        taskEntity.setTitle("Test Title");
        taskEntity.setDescription("Test Description");
        taskEntity.setStatus(TaskStatus.IN_PROGRESS);
        taskEntity.setPriority(TaskPriority.HIGH);

        // When
        TaskResponse taskResponse = underTest.mapToResponse(taskEntity);

        // Then
        assertNotNull(taskResponse);
        assertEquals(taskEntity.getId(), taskResponse.getId());
        assertEquals(taskEntity.getTitle(), taskResponse.getTitle());
        assertEquals(taskEntity.getDescription(), taskResponse.getDescription());
        assertEquals(taskEntity.getStatus(), taskResponse.getStatus());
        assertEquals(taskEntity.getPriority(), taskResponse.getPriority());
    }

    @Test
    void testMapToTaskEntity() {
        // Given
        TaskCreateRequest taskCreateRequest = new TaskCreateRequest();
        taskCreateRequest.setTitle("New Task Title");
        taskCreateRequest.setDescription("New Task Description");
        taskCreateRequest.setStatus(TaskStatus.TODO);
        taskCreateRequest.setPriority(TaskPriority.HIGH);

        // When
        TaskEntity taskEntity = underTest.mapToTaskEntity(taskCreateRequest);

        // Then
        assertNotNull(taskEntity);
        assertNull(taskEntity.getId());
        assertEquals(taskCreateRequest.getTitle(), taskEntity.getTitle());
        assertEquals(taskCreateRequest.getDescription(), taskEntity.getDescription());
        assertEquals(taskCreateRequest.getStatus(), taskEntity.getStatus());
        assertEquals(taskCreateRequest.getPriority(), taskEntity.getPriority());
        assertFalse(taskEntity.isDeleted());
    }

    @Test
    void testMapForUpdate() {
        // Given
        TaskEntity existingTaskEntity = new TaskEntity();
        existingTaskEntity.setId(1L);
        existingTaskEntity.setTitle("Old Title");
        existingTaskEntity.setDescription("Old Description");
        existingTaskEntity.setStatus(TaskStatus.DONE);
        existingTaskEntity.setPriority(TaskPriority.MED);
        existingTaskEntity.setDeleted(true);

        TaskCreateRequest taskUpdateRequest = new TaskCreateRequest();
        taskUpdateRequest.setTitle("Updated Title");
        taskUpdateRequest.setDescription("Updated Description");
        taskUpdateRequest.setStatus(TaskStatus.IN_PROGRESS);
        taskUpdateRequest.setPriority(TaskPriority.HIGH);

        // When
        TaskEntity updatedTaskEntity = underTest.mapForUpdate(existingTaskEntity, taskUpdateRequest);

        // Then
        assertNotNull(updatedTaskEntity);
        assertEquals(existingTaskEntity.getId(), updatedTaskEntity.getId());
        assertEquals(taskUpdateRequest.getTitle(), updatedTaskEntity.getTitle());
        assertEquals(taskUpdateRequest.getDescription(), updatedTaskEntity.getDescription());
        assertEquals(taskUpdateRequest.getStatus(), updatedTaskEntity.getStatus());
        assertEquals(taskUpdateRequest.getPriority(), updatedTaskEntity.getPriority());
        assertTrue(updatedTaskEntity.isDeleted());
        assertSame(existingTaskEntity, updatedTaskEntity);
    }
}
