package kg.nurtelecom.internlabs.taskmanager.notification;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "notification-config")
public class NotificationConfig {
    private List<NotificationRecipient> recipients;
    @Setter
    @Getter
    public static class NotificationRecipient {
        private String recipientType;
        private Map<String, Long> templateIds;
    }
}

