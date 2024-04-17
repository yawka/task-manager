package kg.nurtelecom.internlabs.taskmanager.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import kg.nurtelecom.internlabs.taskmanager.model.Project;
import kg.nurtelecom.internlabs.taskmanager.model.ProjectConstraints;
import kg.nurtelecom.internlabs.taskmanager.payload.projectdto.ProjectRequestDTO;
import kg.nurtelecom.internlabs.taskmanager.payload.projectdto.ProjectResponseDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-26T16:15:55+0600",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class ProjectsMapperImpl implements ProjectsMapper {

    @Override
    public ProjectResponseDTO projectToProjectResponseDTO(Project project) {
        if ( project == null ) {
            return null;
        }

        ProjectResponseDTO projectResponseDTO = new ProjectResponseDTO();

        projectResponseDTO.setProjectId( project.getProjectId() );
        projectResponseDTO.setProjectName( project.getProjectName() );
        projectResponseDTO.setDescription( project.getDescription() );
        projectResponseDTO.setCreatedDate( project.getCreatedDate() );
        projectResponseDTO.setParentProject( project.getParentProject() );
        List<ProjectConstraints> list = project.getConstraints();
        if ( list != null ) {
            projectResponseDTO.setConstraints( new ArrayList<ProjectConstraints>( list ) );
        }

        return projectResponseDTO;
    }

    @Override
    public Project toProject(ProjectRequestDTO projectRequestDto) {
        if ( projectRequestDto == null ) {
            return null;
        }

        Project project = new Project();

        project.setProjectId( projectRequestDto.getProjectId() );
        project.setProjectName( projectRequestDto.getProjectName() );
        project.setParentProject( projectRequestDto.getParentProject() );
        project.setDescription( projectRequestDto.getDescription() );
        project.setCreatedDate( projectRequestDto.getCreatedDate() );
        Set<Long> set = projectRequestDto.getUserIds();
        if ( set != null ) {
            project.setUserIds( new HashSet<Long>( set ) );
        }
        List<ProjectConstraints> list = projectRequestDto.getConstraints();
        if ( list != null ) {
            project.setConstraints( new ArrayList<ProjectConstraints>( list ) );
        }

        project.setDeleted( false );

        return project;
    }
}
