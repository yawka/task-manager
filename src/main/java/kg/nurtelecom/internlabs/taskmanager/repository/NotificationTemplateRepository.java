package kg.nurtelecom.internlabs.taskmanager.repository;

import kg.nurtelecom.internlabs.taskmanager.model.NotificationTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplate, Long> {
}
