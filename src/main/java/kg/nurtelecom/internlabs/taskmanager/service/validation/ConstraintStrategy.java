package kg.nurtelecom.internlabs.taskmanager.service.validation;

import kg.nurtelecom.internlabs.taskmanager.payload.taskdto.TaskRequestDTO;

public interface ConstraintStrategy {
    boolean validate(TaskRequestDTO taskRequestDTO);
}
