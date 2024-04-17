package kg.nurtelecom.internlabs.taskmanager.notification.factory;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import kg.nurtelecom.internlabs.taskmanager.model.Notification;
import kg.nurtelecom.internlabs.taskmanager.model.User;
import kg.nurtelecom.internlabs.taskmanager.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class EmailNotificationService implements NotificationService {
    private final JavaMailSender javaMailSender;
    private final NotificationRepository notificationRepository;

    @Value("${spring.mail.username}")
    private String fromMail;

    public EmailNotificationService(JavaMailSender javaMailSender, NotificationRepository notificationRepository) {
        this.javaMailSender = javaMailSender;
        this.notificationRepository = notificationRepository;
    }
    @Override
    public void sendNotification(User user, String messageSubject, String messageContent) {
        Notification notification = new Notification(user, LocalDateTime.now(),
                messageSubject, messageContent,NotificationType.EMAIL);
        try {
            sendMail(notification);
        } catch (MailSendException | MessagingException sme) {
            notification.setErrorMessage(Objects.requireNonNull(sme.getMessage()).substring(0, 30));
        }
        notificationRepository.save(notification);
    }

    public void sendMail(Notification notification) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);

        mimeMessageHelper.setFrom(fromMail);
        mimeMessageHelper.setTo(notification.getRecipient().getEmail());
        mimeMessageHelper.setSubject(notification.getSubject());
        mimeMessageHelper.setText(notification.getContent(), true);
        javaMailSender.send(mimeMessage);
    }

}
