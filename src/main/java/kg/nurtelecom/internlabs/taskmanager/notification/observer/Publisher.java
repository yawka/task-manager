package kg.nurtelecom.internlabs.taskmanager.notification.observer;

import kg.nurtelecom.internlabs.taskmanager.notification.event.Event;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Publisher {
    private final List<Observer> observers = new ArrayList<>();
    @Async
    public void notifyObservers(Event event) {
        for (Observer observer : observers) {
            observer.update(event);
        }
    }
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
}
