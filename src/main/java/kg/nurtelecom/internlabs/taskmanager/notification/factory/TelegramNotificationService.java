package kg.nurtelecom.internlabs.taskmanager.notification.factory;

import kg.nurtelecom.internlabs.taskmanager.model.User;
import org.springframework.stereotype.Service;

@Service
public class TelegramNotificationService implements NotificationService{
    @Override
    public void sendNotification(User user, String messageSubject, String messageContent) {
        System.out.println("tg +: "+messageContent);
    }
}
