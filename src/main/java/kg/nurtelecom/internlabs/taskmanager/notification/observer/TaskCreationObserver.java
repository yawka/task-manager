package kg.nurtelecom.internlabs.taskmanager.notification.observer;

import jakarta.annotation.PostConstruct;
import kg.nurtelecom.internlabs.taskmanager.notification.NotificationConfig;
import kg.nurtelecom.internlabs.taskmanager.notification.NotificationSender;
import kg.nurtelecom.internlabs.taskmanager.notification.event.Event;
import kg.nurtelecom.internlabs.taskmanager.notification.event.EventType;
import kg.nurtelecom.internlabs.taskmanager.notification.event.TaskEvent;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class TaskCreationObserver implements Observer {

    private final Publisher publisher;
    private final NotificationConfig notificationConfig;
    private final NotificationSender notificationSender;

    public TaskCreationObserver(Publisher publisher, NotificationConfig notificationConfig, NotificationSender notificationSender) {
        this.publisher = publisher;
        this.notificationConfig = notificationConfig;
        this.notificationSender = notificationSender;
    }
    @Override
    public void update(Event event) {
        if (event.getType() == EventType.TASK_CREATED) {
            List<NotificationConfig.NotificationRecipient> recipients = notificationConfig.getRecipients();
            notificationSender.sendNotificationToRecipients(recipients, (TaskEvent) event);
        }
    }

    @PostConstruct
    public void init() {
        publisher.addObserver(this);
    }
}
