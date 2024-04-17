    package kg.nurtelecom.internlabs.taskmanager.repository;

    import kg.nurtelecom.internlabs.taskmanager.model.Project;
    import kg.nurtelecom.internlabs.taskmanager.model.User;
    import kg.nurtelecom.internlabs.taskmanager.payload.userdto.UserRequestDTO;
    import kg.nurtelecom.internlabs.taskmanager.payload.userdto.UserResponseDTO;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.data.repository.query.Param;

    import java.util.List;
    import java.util.Optional;
    import java.util.Set;

    public interface ProjectRepository extends JpaRepository<Project, Long> {
        Page<Project> findAllByDeletedFalse(Pageable pageable);

        @Query("SELECT p FROM Project p WHERE :userId MEMBER OF p.userIds OR p.creator.userId = :userId")
        Page<Project> findByUserIdsOrCreator(@Param("userId") Long userId, Pageable pageable);

        List<Project> findByParentProject(Project project);

        @Query("SELECT p.userIds, p.creator.userId FROM Project p WHERE p.projectId = :projectId AND p.deleted = false")
        Page<UserResponseDTO> findUserIdsAndCreatorByProjectId(@Param("projectId") Long projectId, Pageable pageable);

        Optional<Project> findByProjectIdAndDeletedFalse(Long projectId);
    }
