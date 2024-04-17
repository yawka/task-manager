package kg.nurtelecom.internlabs.taskmanager.notification.factory;

import org.springframework.stereotype.Component;

@Component
public class NotificationServiceFactory {
    private final EmailNotificationService emailNotificationService;
    private final TelegramNotificationService telegramNotificationService;

    public NotificationServiceFactory(EmailNotificationService emailNotificationService, TelegramNotificationService telegramNotificationService) {
        this.emailNotificationService = emailNotificationService;
        this.telegramNotificationService = telegramNotificationService;
    }

    public NotificationService getService(NotificationType type) {
        switch (type) {
            case EMAIL:
                return emailNotificationService;
            case TELEGRAM:
                return telegramNotificationService;
            default:
                break;
        }
        return null;
    }
}
