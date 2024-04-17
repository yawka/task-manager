package kg.nurtelecom.internlabs.taskmanager.mapper;

import kg.nurtelecom.internlabs.taskmanager.payload.projectdto.ProjectRequestDTO;
import kg.nurtelecom.internlabs.taskmanager.payload.projectdto.ProjectResponseDTO;
import kg.nurtelecom.internlabs.taskmanager.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring",  uses = {UserMapper.class})
public interface  ProjectsMapper {
    ProjectsMapper INSTANCE = Mappers.getMapper(ProjectsMapper.class);
    ProjectResponseDTO projectToProjectResponseDTO(Project project);

    @Mapping(target = "deleted", expression = "java(false)")
    Project toProject(ProjectRequestDTO projectRequestDto);
}
