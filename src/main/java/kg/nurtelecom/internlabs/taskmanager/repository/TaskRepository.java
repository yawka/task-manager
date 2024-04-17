package kg.nurtelecom.internlabs.taskmanager.repository;

import jakarta.transaction.Transactional;
import kg.nurtelecom.internlabs.taskmanager.model.Project;
import kg.nurtelecom.internlabs.taskmanager.model.Task;
import kg.nurtelecom.internlabs.taskmanager.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {


    Task findTaskByStartDate(LocalDateTime startDate);
    Task findTaskByEndDate(LocalDateTime endDate);

    Page<Task> findByProjectAndIsDeletedFalse(Project project, Pageable pageable);

    Optional<Task> findTaskByTaskIdAndIsDeletedFalse(Long taskId);

    @Query("SELECT t FROM Task t WHERE t.creator = :user OR t.executor = :user")
    Page<Task> findAllByCreatorOrExecutor(@Param("user") User user, Pageable pageable);

    @Query("SELECT t FROM Task t WHERE t.isDeleted = false AND t.project = :project AND (t.creator = :user OR t.executor = :user)")
    Page<Task> findTasksByProjectAndUserAndNotDeleted(@Param("project") Project project, @Param("user") User user, Pageable pageable);

    @Query(value = "SELECT * FROM Task WHERE CAST(start_date AS DATE) = CURRENT_DATE AND is_deleted = FALSE", nativeQuery = true)
    List<Task> findTasksStartedTodayAndNotDeleted();

    @Transactional
    @Modifying
    @Query("UPDATE Task t SET t.isDeleted = true WHERE t.project = :project")
    void markAllTasksByProjectAsDeleted(@Param("project") Project project);

    @Transactional
    @Modifying
    @Query("UPDATE Task t SET t.isDeleted = true WHERE t.parentTask.taskId = :parentTaskId")
    void markSubTasksAsDeleted(@Param("parentTaskId") Long parentTaskId);

    @Transactional
    @Modifying
    @Query("UPDATE Task t SET t.isDeleted = true WHERE t.taskId = :taskId")
    void markTaskAsDeleted(@Param("taskId") Long taskId);

    long countByParentTaskTaskId(Long parentTaskId);

    @Query("SELECT t FROM Task t WHERE t.project.projectId = :projectId AND t.isDeleted = false")
    List<Task> findByProjectIdAndIsDeletedFalse(@Param("projectId") Long projectId);


}
