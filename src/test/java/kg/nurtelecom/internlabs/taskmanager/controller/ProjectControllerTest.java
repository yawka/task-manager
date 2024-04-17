//package kg.nurtelecom.internlabs.taskmanager.controller;
//
//import kg.nurtelecom.internlabs.taskmanager.payload.projectdto.ProjectResponseDTO;
//import org.junit.jupiter.api.*;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.CsvSource;
//import org.junit.jupiter.params.provider.MethodSource;
//import org.junit.jupiter.params.provider.NullAndEmptySource;
//import org.junit.jupiter.params.provider.ValueSource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import javax.annotation.PostConstruct;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Stream;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//class ProjectControllerTest {
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//
//    private ProjectResponseDTO projectResponseDto;
//    String BASE_URL;
//
//    @PostConstruct
//    void initUrl() {
//        BASE_URL = "http://localhost:" + port + "/projects";
//    }
//
//    private List<Long> generatedProjectIds = new ArrayList<>();
//
//
//    @ParameterizedTest
//    @DisplayName("Create Projects with different types of name and with Boundary values - 254, 255 symbols")
//    @CsvSource({
//            "Test project, Test description",
//            "12, Third description",
//            "!№%:, Name with special characters ",
//            "Valid name and empty description,",
//            "null, null",
//            "Test project1 Test project2 Test project3 Test project4 Test project5 Test project6 Test project7 Test project8 Test project9 Test project1 Test project2 Test project3 Test project4 Test project5 Test project6 Test project7 Test project8 Test project9 Te, Fourth description",
//            "Test project1 Test project2 Test project3 Test project4 Test project5 Test project6 Test project7 Test project8 Test project9 Test project1 Test project2 Test project3 Test project4 Test project5 Test project6 Test project7 Test project8 Test project9 Tes, Fifth description"
//
//    })
//    @Order(1)
//    void createProject(String projectName, String description) {
//        projectResponseDto = ProjectResponseDTO.builder()
//                .projectName(projectName)
//                .description(description)
//                .build();
//
//        assertDoesNotThrow(() -> {
//            ResponseEntity<ProjectResponseDTO> projectDtoResponseEntity = restTemplate.postForEntity(new URI(BASE_URL), projectResponseDto, ProjectResponseDTO.class);
//
//            assertNotNull(projectDtoResponseEntity);
//            assertEquals(HttpStatus.CREATED, projectDtoResponseEntity.getStatusCode());
//            assertNotNull(projectDtoResponseEntity.getBody());
//            ProjectResponseDTO body = projectDtoResponseEntity.getBody();
//            projectResponseDto = body;
//            assertNotNull(body.getProjectId());
//            assertEquals(projectResponseDto.getProjectName(), body.getProjectName());
//            assertEquals(projectResponseDto.getDescription(), body.getDescription());
//
//            generatedProjectIds.add(body.getProjectId());
//        });
//    }
//
//    @ParameterizedTest
//    @DisplayName("Create Projects with invalid values and Project name = 256 symbols")
//    @CsvSource({
//            ", Empty name but correct description",
//            "Test project1 Test project2 Test project3 Test project4 Test project5 Test project6 Test project7 Test project8 Test project9 Test project1 Test project2 Test project3 Test project4 Test project5 Test project6 Test project7 Test project8 Test project9 Test, Name - 256 symbols and description",
//            " , Name consists of only one space and full description",
//
//    })
//    void createProjectWithInvalidValues(String projectName, String description) throws URISyntaxException {
//
//        ResponseEntity<ProjectResponseDTO> responseEntity = restTemplate.postForEntity(
//                new URI(BASE_URL),
//                ProjectResponseDTO.builder()
//                        .projectName(projectName)
//                        .description(description)
//                        .build(),
//                ProjectResponseDTO.class
//        );
//
//        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
//    }
//
//    @Test
//    @DisplayName("Show project by Id")
//    @Order(2)
//    void getProject() {
//        assertDoesNotThrow(() -> {
//
//            assertNotNull(projectResponseDto.getProjectId());
//            ResponseEntity<ProjectResponseDTO> forEntity = restTemplate.getForEntity(new URI(BASE_URL + "/" + projectResponseDto.getProjectId()), ProjectResponseDTO.class);
//            assertNotNull(forEntity);
//            assertEquals(HttpStatus.OK, forEntity.getStatusCode());
//            assertNotNull(forEntity.getBody());
//            ProjectResponseDTO body = forEntity.getBody();
//
//            assertNotNull(body.getProjectId());
//            assertEquals(projectResponseDto.getProjectName(), body.getProjectName());
//            assertEquals(projectResponseDto.getDescription(), body.getDescription());
//
//        });
//
//    }
//
//    @Test
//    void getProjectWithInvalidId() {
//        Long invalidProjectId = Long.MAX_VALUE;
//
//        String url = UriComponentsBuilder.fromHttpUrl(String.format(BASE_URL + "/{projectId}"))
//                .buildAndExpand(invalidProjectId)
//                .toUriString();
//
//        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
//
//        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
//
//    }
//
//
//    @ParameterizedTest
//    @NullAndEmptySource
//    @DisplayName("Update projects with valid name and/or description")
//
//    @CsvSource({"New name, New description",
//            "New Name, {description}",
//            "{projectName}, New description",
//            "{projectName}, {description}",
//
//    })
//
//    @Order(4)
//    void updateProjectWithValidValues(String projectName, String description) {
//
//        ProjectResponseDTO updatedProjectResponseDTO = ProjectResponseDTO.builder()
//                .projectName(projectName == null ? projectResponseDto.getProjectName() : projectName)
//                .description(description == null ? projectResponseDto.getDescription() : description)
//                .build();
//
//        assertDoesNotThrow(() -> {
//            ResponseEntity<ProjectResponseDTO> updatedProjectDTOResponseEntity = restTemplate.exchange(
//                    new URI(BASE_URL + "/" + projectResponseDto.getProjectId()),
//                    HttpMethod.PUT,
//                    new HttpEntity<>(updatedProjectResponseDTO),
//                    ProjectResponseDTO.class);
//
//            System.out.println(updatedProjectDTOResponseEntity);
//            assertNotNull(updatedProjectDTOResponseEntity);
//            assertEquals(HttpStatus.OK, updatedProjectDTOResponseEntity.getStatusCode());
//            assertNotNull(updatedProjectDTOResponseEntity.getBody());
//        });
//    }
//
//    @Test
//    void updateProjectWithNonExistentId() throws Exception {
//        Long invalidProjectId = Long.MAX_VALUE;
//
//        ProjectResponseDTO invalidProjectResponseDTO = ProjectResponseDTO.builder()
//                .projectName("New project Name")
//                .description("New project description")
//                .build();
//
//        try {
//            ResponseEntity<ProjectResponseDTO> invalidProjectDTOResponseEntity = restTemplate.exchange(
//                    new URI(BASE_URL + "/" + invalidProjectId),
//                    HttpMethod.PUT,
//                    new HttpEntity<>(invalidProjectResponseDTO),
//                    ProjectResponseDTO.class);
//            System.out.println("Response: " + invalidProjectDTOResponseEntity.toString());
//
//            assertEquals(HttpStatus.NOT_FOUND, invalidProjectDTOResponseEntity.getStatusCode());
//        } catch (HttpClientErrorException e) {
//            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
//        } catch (Exception e) {
//        }
//    }
//
//
//    @ParameterizedTest
//    @MethodSource("provideProjectIds")
//    @Order(10)
//    void deleteProject(Long projectId) {
//        assertNotNull(projectResponseDto);
//
//        URI uri = UriComponentsBuilder.fromUriString(BASE_URL)
//                .path("/{projectId}")
//                .buildAndExpand(projectId)
//                .toUri();
//
//        ResponseEntity<Void> responseEntity = restTemplate.exchange(
//                uri,
//                HttpMethod.DELETE,
//                null,
//                Void.class);
//
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//
//    }
//
//    Stream<Long> provideProjectIds() {
//        return generatedProjectIds.stream();
//    }
//
//    @Test
//    @Order(11)
//    void deleteProjectWithNonExistingId() {
//        Long invalidProjectId = Long.MAX_VALUE;
//
//        URI uri = UriComponentsBuilder.fromUriString(BASE_URL)
//                .path("/{projectId}")
//                .buildAndExpand(invalidProjectId)
//                .toUri();
//
//        try {
//            ResponseEntity<Void> responseEntity = restTemplate.exchange(
//                    uri,
//                    HttpMethod.DELETE,
//                    null,
//                    Void.class);
//
//            assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
//        } catch (HttpClientErrorException e) {
//            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
//        }
//    }
//
//    @ParameterizedTest
//    @DisplayName("Show different number of projects on the page")
//    @ValueSource(ints = {1, 3, 10})
//    void returnDifferentNumberOfProjects(int projectSize) {
//        String url = UriComponentsBuilder.fromHttpUrl(String.format("http://localhost:%d/projects", port))
//                .queryParam("page", 0)
//                .queryParam("size", projectSize)
//                .queryParam("sort", "projectId,asc")
//                .toUriString();
//
//        ResponseEntity<List<Map<String, Object>>> responseEntity = restTemplate.exchange(
//                url,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<>() {
//                }
//        );
//
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isNotNull();
//        assertThat(responseEntity.getBody()).asList();
//
//        System.out.println("Response Body for" + projectSize + "projects");
//        for (Map<String, Object> item : responseEntity.getBody()) {
//            System.out.println(item);
//        }
//    }
//
//    @ParameterizedTest
//    @DisplayName("Show projects with invalid number")
//    @ValueSource(ints = {-1, 0})
//    void returnProjectsWithInvalidNumber(int invalidNumberOfProjects) {
//        String url = UriComponentsBuilder.fromHttpUrl(String.format("http://localhost:%d/projects", port))
//                .queryParam("page", 0)
//                .queryParam("size", invalidNumberOfProjects)
//                .queryParam("sort", "projectId,asc")
//                .toUriString();
//
//        ResponseEntity<List<Map<String, Object>>> responseEntity = restTemplate.exchange(
//                url,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<>() {
//                }
//        );
//
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//        assertThat(responseEntity.getBody()).isNull();
//    }
//
//    @Test
//    @Order(5)
//    void getProjectsWithNonExistentId() {
//        Long nonExistentProjectId = Long.MAX_VALUE;
//
//        String uri = UriComponentsBuilder.fromHttpUrl(String.format(BASE_URL))
//                .path("/{projectId}")
//                .queryParam("page", 0)
//                .queryParam("size", 10)
//                .buildAndExpand(nonExistentProjectId)
//                .toUriString();
//
//        try {
//            ResponseEntity<String> responseEntity = restTemplate.exchange(
//                    uri,
//                    HttpMethod.GET,
//                    null,
//                    String.class);
//
//            assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
//        } catch (HttpClientErrorException e) {
//            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
//        }
//    }
//
//    @ParameterizedTest
//    @DisplayName("Show projects on different pages")
//    @ValueSource(ints = {0, 1, 2})
//    void returnProjects(int pageNumber) {
//        String url = UriComponentsBuilder.fromHttpUrl(String.format("http://localhost:%d/projects", port))
//                .queryParam("page", pageNumber)
//                .queryParam("size", 10)
//                .queryParam("sort", "projectId,asc")
//                .toUriString();
//
//
//        ResponseEntity<List<Map<String, Object>>> responseEntity = restTemplate.exchange(
//                url,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<>() {
//                }
//        );
//
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(responseEntity.getBody()).isNotNull();
//        assertThat(responseEntity.getBody()).asList();
//
//        System.out.println("Response Body for Page: " + pageNumber);
//        for (Map<String, Object> item : responseEntity.getBody()) {
//            System.out.println(item);
//        }
//    }
//
//    @ParameterizedTest
//    @DisplayName("Show projects with invalid page numbers and sizes")
//    @CsvSource({
//            "-1, 10",
//            "0, 0",
//            "1, -5",
//            "2, 100"
//    })
//    void returnProjectsWithInvalidParams(int invalidPageNumber, int invalidSize) {
//        String url = UriComponentsBuilder.fromHttpUrl(String.format("http://localhost:%d/projects", port))
//                .queryParam("page", invalidPageNumber)
//                .queryParam("size", invalidSize)
//                .queryParam("sort", "projectId,asc")
//                .toUriString();
//
//        ResponseEntity<List<Map<String, Object>>> responseEntity = restTemplate.exchange(
//                url,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<>() {
//                }
//        );
//
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//        assertThat(responseEntity.getBody()).isNull();
//    }
//
//}