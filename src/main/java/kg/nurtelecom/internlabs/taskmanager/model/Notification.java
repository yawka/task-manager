package kg.nurtelecom.internlabs.taskmanager.model;

import jakarta.persistence.*;
import kg.nurtelecom.internlabs.taskmanager.notification.factory.NotificationType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="recipient")
    private User recipient;

    @CreationTimestamp
    private LocalDateTime sendDate;

    @Column(name = "subject", columnDefinition = "TEXT")
    private String subject;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "error_message")
    private String errorMessage;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type")
    private NotificationType notificationType;

    public Notification(User recipient, LocalDateTime sendDate, String subject, String content, NotificationType notificationType) {
        this.recipient = recipient;
        this.sendDate = sendDate;
        this.content = content;
        this.subject = subject;
        this.notificationType = notificationType;
    }
}
