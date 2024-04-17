package kg.nurtelecom.internlabs.taskmanager.service.validation.strategies;

import kg.nurtelecom.internlabs.taskmanager.payload.taskdto.TaskRequestDTO;
import kg.nurtelecom.internlabs.taskmanager.repository.TaskRepository;
import kg.nurtelecom.internlabs.taskmanager.service.validation.ConstraintStrategy;
import org.springframework.stereotype.Component;

@Component
public class NoOverlapConstraint implements ConstraintStrategy {

    private final TaskRepository taskRepository;

    public NoOverlapConstraint(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public boolean validate(TaskRequestDTO taskRequestDTO) {
        return taskRepository.findTaskByEndDate(taskRequestDTO.getEndDate()) == null && taskRepository.findTaskByStartDate(taskRequestDTO.getStartDate()) == null;
    }
}
