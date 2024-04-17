package kg.nurtelecom.internlabs.taskmanager.notification;

import kg.nurtelecom.internlabs.taskmanager.notification.event.TaskEvent;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class NotificationMessageFormatter {

    private final TemplateEngine templateEngine;

    public NotificationMessageFormatter(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String formatMessage(TaskEvent taskEvent) {
        Context context = new Context();
        context.setVariable("taskName", taskEvent.getTask().getTaskName());
        context.setVariable("description", taskEvent.getTask().getDescription());
        context.setVariable("creator", taskEvent.getTask().getCreator().getUsername());
        context.setVariable("startDate", taskEvent.getTask().getStartDate());
        context.setVariable("projectName", taskEvent.getTask().getProject().getProjectName());

        return templateEngine.process(taskEvent.getType().getEmailTemplate(), context);
    }



}
