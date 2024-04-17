package kg.nurtelecom.internlabs.taskmanager.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "notification_template")
public class NotificationTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "subject", columnDefinition = "TEXT")
    private String subject;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
}
