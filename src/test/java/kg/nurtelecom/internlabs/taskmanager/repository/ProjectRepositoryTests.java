package kg.nurtelecom.internlabs.taskmanager.repository;

import kg.nurtelecom.internlabs.taskmanager.model.Project;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
@DataJpaTest
public class ProjectRepositoryTests {
    @Autowired
    private ProjectRepository projectRepository;
    private Project project;

    @BeforeEach
    public void setupTestData() {
        project = Project.builder()
                .projectName("TestProjectRepo")
                .description("Description")
                .build();
    }

    @Test
    @DisplayName("Test for saving a project in the repository")
    public void whenProjectObjectSaved_thenSaveProjectNotNullAndHasId() {
        Project saveProject = projectRepository.save(project);
        assertThat(saveProject).isNotNull();
        assertThat(saveProject.getProjectId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Test for retrieving Project List from Repository")
    public void whenProjectsSavedAndFindAll_thenListNotEmptyAndSizeMatches() {
        Project projectOne = Project.builder()
                .projectName("TestProjectRepo 1 ")
                .description("Description")
                .build();
        Project projectTwo = Project.builder()
                .projectName("TestProjectRepo 2")
                .description("Description")
                .build();

        projectRepository.save(projectOne);
        projectRepository.save(projectTwo);

        List<Project> projects = projectRepository.findAll();
        Assertions.assertThat(projects).isNotEmpty();
        assertThat(projects.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Test for retrieving a Project by its ID from the repository")
    public void whenProjectSavedAndFoundById_thenProjectNotNull() {
        projectRepository.save(project);
        Project getProject = projectRepository.findById(project.getProjectId()).get();
        assertThat(getProject).isNotNull();
    }

    @Test
    @DisplayName("Test for updating a project in the repository")
    public void whenProjectUpdated_thenUpdatedProjectNotNullAndNameMatches() {
        projectRepository.save(project);

        Project getProject = projectRepository.findById(project.getProjectId()).get();

        getProject.setProjectName("TestProjectRepo update");
        getProject.setDescription("TestProjectRepo desc");

        Project updatedProject = projectRepository.save(getProject);

        assertThat(updatedProject).isNotNull();
        assertThat(updatedProject.getProjectName()).isEqualTo("TestProjectRepo update");
    }

    @Test
    @DisplayName("Test for deleting a project from the repository")
    public void whenProjectDeleted_thenProjectNotFound() {
        projectRepository.save(project);
        projectRepository.deleteById(project.getProjectId());

        Optional<Project> deleteProject = projectRepository.findById(project.getProjectId());

        assertThat(deleteProject).isEmpty();
    }


}
