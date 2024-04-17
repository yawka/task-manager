package kg.nurtelecom.internlabs.taskmanager.notification.observer;

import kg.nurtelecom.internlabs.taskmanager.notification.event.Event;

public interface Observer {
    void update(Event event);
}

