package kg.nurtelecom.internlabs.taskmanager.notification.event;

import kg.nurtelecom.internlabs.taskmanager.model.Task;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskEvent extends Event{
    private Task task;
    public TaskEvent(EventType type, Task task) {
        super(type);
        this.task=task;
    }
}

