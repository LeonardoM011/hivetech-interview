package hr.leonardom011.hivetechinterview.tasks.repository;

import hr.leonardom011.hivetechinterview.constant.TaskPriority;
import hr.leonardom011.hivetechinterview.constant.TaskStatus;
import hr.leonardom011.hivetechinterview.tasks.model.entity.TaskEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    @Query(value = "SELECT t FROM TaskEntity t " +
            "WHERE t.isDeleted = false " +
            "AND (t.status = :status or :status is null)")
    Page<TaskEntity> findByIsDeletedFalseAndStatus(TaskStatus status, Pageable pageable);

    Optional<TaskEntity> findByIdAndIsDeletedFalse(Long id);

    @Transactional
    @Modifying
    @Query("""
            update TaskEntity t set t.title = :title, t.description = :description, t.status = :status, t.priority = :priority
            where t.isDeleted = false""")
    int updateTitleAndDescriptionAndStatusAndPriorityByIsDeletedFalse(String title, String description, TaskStatus status, TaskPriority priority);
}
