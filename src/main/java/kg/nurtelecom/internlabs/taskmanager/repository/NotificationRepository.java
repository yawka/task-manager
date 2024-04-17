package kg.nurtelecom.internlabs.taskmanager.repository;

import kg.nurtelecom.internlabs.taskmanager.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
