package kg.nurtelecom.internlabs.taskmanager.notification.event;

import lombok.Getter;

@Getter
public enum EventType {
    TASK_CREATED("task-created"),
    TASK_STARTED("task-started");

    private final String emailTemplate;

    EventType(String emailTemplate) {
        this.emailTemplate = emailTemplate;
    }

}
