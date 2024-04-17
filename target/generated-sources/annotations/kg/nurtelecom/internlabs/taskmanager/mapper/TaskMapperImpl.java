package kg.nurtelecom.internlabs.taskmanager.mapper;

import java.time.format.DateTimeFormatter;
import javax.annotation.processing.Generated;
import kg.nurtelecom.internlabs.taskmanager.model.Task;
import kg.nurtelecom.internlabs.taskmanager.payload.taskdto.TaskRequestDTO;
import kg.nurtelecom.internlabs.taskmanager.payload.taskdto.TaskResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-01T15:57:13+0600",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class TaskMapperImpl implements TaskMapper {

    @Autowired
    private UserMapper userMapper;

    @Override
    public TaskResponseDTO toTaskResponseDTO(Task task) {
        if ( task == null ) {
            return null;
        }

        TaskResponseDTO taskResponseDTO = new TaskResponseDTO();

        taskResponseDTO.setParentTaskId( taskParentTaskTaskId( task ) );
        taskResponseDTO.setTaskId( task.getTaskId() );
        taskResponseDTO.setTaskName( task.getTaskName() );
        taskResponseDTO.setDescription( task.getDescription() );
        if ( task.getEndDate() != null ) {
            taskResponseDTO.setEndDate( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( task.getEndDate() ) );
        }
        if ( task.getStartDate() != null ) {
            taskResponseDTO.setStartDate( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( task.getStartDate() ) );
        }
        taskResponseDTO.setDuration( task.getDuration() );
        taskResponseDTO.setProgress( task.getProgress() );
        taskResponseDTO.setCreator( userMapper.toUserResponseDTO( task.getCreator() ) );
        taskResponseDTO.setExecutor( userMapper.toUserResponseDTO( task.getExecutor() ) );

        return taskResponseDTO;
    }

    @Override
    public Task toTask(TaskRequestDTO taskRequestDTO) {
        if ( taskRequestDTO == null ) {
            return null;
        }

        Task task = new Task();

        task.setTaskId( taskRequestDTO.getTaskId() );
        task.setTaskName( taskRequestDTO.getTaskName() );
        task.setDescription( taskRequestDTO.getDescription() );
        task.setStartDate( taskRequestDTO.getStartDate() );
        task.setEndDate( taskRequestDTO.getEndDate() );
        task.setDuration( taskRequestDTO.getDuration() );
        task.setProgress( taskRequestDTO.getProgress() );
        task.setProject( taskRequestDTO.getProject() );
        task.setParentTask( taskRequestDTO.getParentTask() );
        task.setExecutor( userMapper.toUser( taskRequestDTO.getExecutor() ) );

        task.setIsDeleted( false );

        return task;
    }

    private Long taskParentTaskTaskId(Task task) {
        if ( task == null ) {
            return null;
        }
        Task parentTask = task.getParentTask();
        if ( parentTask == null ) {
            return null;
        }
        Long taskId = parentTask.getTaskId();
        if ( taskId == null ) {
            return null;
        }
        return taskId;
    }
}
