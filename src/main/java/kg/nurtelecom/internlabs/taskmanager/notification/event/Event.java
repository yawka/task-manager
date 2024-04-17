package kg.nurtelecom.internlabs.taskmanager.notification.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Event {
    private EventType type;
    public Event(EventType type) {
        this.type=type;
    }
}
