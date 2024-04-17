package kg.nurtelecom.internlabs.taskmanager.notification.factory;

import kg.nurtelecom.internlabs.taskmanager.model.User;

public interface NotificationService {
    void sendNotification(User user, String messageSubject, String messageContent);
}
