package kg.nurtelecom.internlabs.taskmanager.service.validation;

import kg.nurtelecom.internlabs.taskmanager.payload.taskdto.TaskRequestDTO;
import org.springframework.stereotype.Component;

@Component
public class ConstraintContext {
    private ConstraintStrategy strategy;

    public void setStrategy(ConstraintStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean validate(TaskRequestDTO taskRequestDto ) {
        return strategy.validate(taskRequestDto);
    }
}
