package kg.nurtelecom.internlabs.taskmanager.model;

import jakarta.persistence.*;
import kg.nurtelecom.internlabs.taskmanager.model.enums.ProjectConstraints;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "project")
@Data
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "project_name", length = 255)
    private String projectName;

    @ManyToOne
    @JoinColumn(name = "parent_project_id")
    private Project parentProject;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_date")
    @CreationTimestamp
    private LocalDateTime createdDate;

    @Column(name = "deleted")
    private Boolean deleted;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "project_user", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "user_id")
    private Set<Long> userIds;

    @ElementCollection(targetClass = ProjectConstraints.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "project_constraints", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "constraint_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private List<ProjectConstraints> constraints;
}

