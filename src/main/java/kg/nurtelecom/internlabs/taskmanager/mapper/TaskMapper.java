package kg.nurtelecom.internlabs.taskmanager.mapper;

import kg.nurtelecom.internlabs.taskmanager.payload.taskdto.TaskRequestDTO;
import kg.nurtelecom.internlabs.taskmanager.payload.taskdto.TaskResponseDTO;
import kg.nurtelecom.internlabs.taskmanager.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",  uses = {UserMapper.class, ProjectsMapper.class})
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    @Mapping(target = "parentTaskId", source = "parentTask.taskId")
    TaskResponseDTO toTaskResponseDTO(Task task);
    @Mapping(target = "isDeleted", expression = "java(false)")
    Task toTask(TaskRequestDTO taskRequestDTO);
}
