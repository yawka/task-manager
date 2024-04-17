package kg.nurtelecom.internlabs.taskmanager.notification;

import kg.nurtelecom.internlabs.taskmanager.model.Task;
import kg.nurtelecom.internlabs.taskmanager.notification.event.EventType;
import kg.nurtelecom.internlabs.taskmanager.notification.event.TaskEvent;
import kg.nurtelecom.internlabs.taskmanager.notification.observer.Publisher;
import kg.nurtelecom.internlabs.taskmanager.repository.TaskRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationSchedulerService {
    private final TaskRepository taskRepository;

    private final Publisher publisher;

    public NotificationSchedulerService(TaskRepository taskRepository, Publisher publisher) {
        this.taskRepository = taskRepository;
        this.publisher = publisher;
    }

    @Scheduled(cron = "0 00 10 * * ?", zone = "GMT+6")
    public void checkTaskStartTimes() {
        List<Task> tasks = taskRepository.findTasksStartedTodayAndNotDeleted();
        for (Task task : tasks) {
            publisher.notifyObservers(new TaskEvent(EventType.TASK_STARTED, task));
        }
    }
}

