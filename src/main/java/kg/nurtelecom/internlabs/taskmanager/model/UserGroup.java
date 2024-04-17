package kg.nurtelecom.internlabs.taskmanager.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_group")
@Data
public class UserGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_group_id")
    private Long userGroupId;

    @Column(name = "group_name")
    private String groupName;

    @ManyToOne
    @JoinColumn(name = "parent_group_id")
    private UserGroup parentGroup;

    @ManyToOne
    @JoinColumn(name = "creator_id", nullable = false)
    private User creator;
}
