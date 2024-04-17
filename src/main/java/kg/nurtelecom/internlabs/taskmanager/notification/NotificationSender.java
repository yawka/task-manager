package kg.nurtelecom.internlabs.taskmanager.notification;

import kg.nurtelecom.internlabs.taskmanager.exception.NotificationTemplateNotFoundException;
import kg.nurtelecom.internlabs.taskmanager.exception.RecipientNotFoundException;
import kg.nurtelecom.internlabs.taskmanager.model.NotificationTemplate;
import kg.nurtelecom.internlabs.taskmanager.model.User;
import kg.nurtelecom.internlabs.taskmanager.notification.event.TaskEvent;
import kg.nurtelecom.internlabs.taskmanager.notification.factory.NotificationService;
import kg.nurtelecom.internlabs.taskmanager.notification.factory.NotificationServiceFactory;
import kg.nurtelecom.internlabs.taskmanager.notification.factory.NotificationType;
import kg.nurtelecom.internlabs.taskmanager.repository.NotificationTemplateRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static kg.nurtelecom.internlabs.taskmanager.notification.factory.NotificationType.*;

@Component
public class NotificationSender {
    private final NotificationTemplateRepository notificationTemplateRepository;
    private final NotificationServiceFactory notificationServiceFactory;
    private final NotificationMessageFormatter notificationMessageFormatter;

    public NotificationSender(NotificationTemplateRepository notificationTemplateRepository, NotificationServiceFactory notificationServiceFactory, NotificationMessageFormatter notificationMessageFormatter) {
        this.notificationTemplateRepository = notificationTemplateRepository;
        this.notificationServiceFactory = notificationServiceFactory;
        this.notificationMessageFormatter = notificationMessageFormatter;
    }

    public void sendNotification(User user, NotificationType notificationType, String messageSubject,String messageContent) {
        NotificationService notificationService = notificationServiceFactory.getService(notificationType);
        notificationService.sendNotification(user, messageSubject, messageContent);
    }


    public User getUserByRecipientType(TaskEvent taskEvent, String recipientType) {
        return switch (recipientType) {
            case "creator" -> taskEvent.getTask().getCreator();
            case "assigner" -> taskEvent.getTask().getCreator();
            case "team-lead" -> taskEvent.getTask().getCreator().getDirectSupervisor();
            default -> throw new RecipientNotFoundException("Invalid recipient type: " + recipientType);
        };
    }

    public void sendNotificationToRecipients(List<NotificationConfig.NotificationRecipient> recipients, TaskEvent taskEvent) {
        for (NotificationConfig.NotificationRecipient recipient : recipients) {
            Map<String, Long> templateIds = recipient.getTemplateIds();
            Long templateId = templateIds.get(taskEvent.getType().toString());

            if (templateId == null) {
                throw new NotificationTemplateNotFoundException("Template not found for event type: " + taskEvent.getType().toString());
            }

            NotificationTemplate notificationTemplate = notificationTemplateRepository.findById(templateId)
                    .orElseThrow(() -> new NotificationTemplateNotFoundException("Template not found with id: " + templateId));

            String messageContent = notificationMessageFormatter.formatMessage(taskEvent);
            String messageSubject = notificationTemplate.getSubject();


            User recipientUser = getUserByRecipientType(taskEvent, recipient.getRecipientType());
            if (recipientUser == null) {
                break;
            }
            sendNotification(recipientUser, EMAIL, messageSubject, messageContent);
//            switch (recipientUser.getNotificationPreference()) {
//                case NONE:
//                    break;
//                case EMAIL:
//                    sendNotification(recipientUser, EMAIL, messageSubject, messageContent);
//                    break;
//                case TELEGRAM:
//                    sendNotification(recipientUser, TELEGRAM, messageSubject, messageContent);
//                    break;
//                case BOTH:
//                    sendNotification(recipientUser, EMAIL, messageSubject, messageContent);
//                    sendNotification(recipientUser, TELEGRAM, messageSubject, messageContent);
//                    break;
//                default:
//                    throw new IllegalArgumentException("Unknown notification preference: " + recipientUser.getNotificationPreference());
//            }
        }
    }

}
