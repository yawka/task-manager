//package kg.nurtelecom.internlabs.taskmanager.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import kg.nurtelecom.internlabs.taskmanager.dto.projectdto.ProjectResponseDTO;
//import kg.nurtelecom.internlabs.taskmanager.dto.projectdto.ProjectRequestDTO;
//import kg.nurtelecom.internlabs.taskmanager.dto.taskdto.TaskResponseDTO;
//import kg.nurtelecom.internlabs.taskmanager.dto.taskdto.TaskRequestDTO;
//import kg.nurtelecom.internlabs.taskmanager.exception.TaskNotFoundException;
//import kg.nurtelecom.internlabs.taskmanager.service.TaskService;
//import org.junit.jupiter.api.*;
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
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.util.UriComponentsBuilder;
//
//import javax.annotation.PostConstruct;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//class TaskControllerTest {
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Autowired
//    private TaskService taskService;
//
//
//    private ProjectResponseDTO project;
//    private TaskResponseDTO task;
//
//
//    String BASE_URL;
//
//    @PostConstruct
//    void initUrl() {
//        BASE_URL = "http://localhost:" + port;
//    }
//
//    @Test
//    @Order(1)
//    void contextLoads() throws URISyntaxException {
//
//        ProjectResponseDTO projectInputDto = ProjectResponseDTO.builder()
//                .projectName("Test project Name")
//                .description("Test description")
//                .build();
//        ResponseEntity<ProjectResponseDTO> projectDtoResponseEntity = restTemplate.postForEntity(new URI(BASE_URL + "/projects"), projectInputDto, ProjectResponseDTO.class);
//        project = projectDtoResponseEntity.getBody();
//    }
//
//
//    @Test
//    @Order(2)
//    void createTask() {
//        TaskRequestDTO taskRequestDto = TaskRequestDTO.builder()
//                .taskName("Test task Name")
//                .description("Test task description")
//                .projectId(project.getProjectId())
//                .build();
//
//        assertDoesNotThrow(() -> {
//            ResponseEntity<TaskResponseDTO> taskDtoResponseEntity = restTemplate.postForEntity(
//                    new URI(BASE_URL + "/tasks"),
//                    taskRequestDto,
//                    TaskResponseDTO.class
//            );
//
//            System.out.println(taskDtoResponseEntity);
//            assertNotNull(taskDtoResponseEntity);
//            assertEquals(HttpStatus.CREATED, taskDtoResponseEntity.getStatusCode());
//            assertNotNull(taskDtoResponseEntity.getBody());
//
//            TaskResponseDTO body = taskDtoResponseEntity.getBody();
//            task = body;
//            assertNotNull(body.getTaskId());
//        });
//    }
//
//
//    @Test
//    @Order(3)
//    void createTaskWithoutProject() {
//        TaskRequestDTO invalidTaskRequestDto = TaskRequestDTO.builder()
//                .taskName("Sample Task")
//                .description("Sample description")
//                .projectId(null)
//                .build();
//
//        assertThrows(HttpClientErrorException.BadRequest.class, () -> {
//            ResponseEntity<TaskResponseDTO> taskDtoResponseEntity = restTemplate.postForEntity(
//                    new URI(BASE_URL + "/tasks"),
//                    invalidTaskRequestDto,
//                    TaskResponseDTO.class
//            );
//            assertEquals(HttpStatus.BAD_REQUEST, taskDtoResponseEntity.getStatusCode());
//        });
//
//    } //Expected HttpClientErrorException BadRequest, but nothing was thrown
//
//    @Test
//    @Order(4)
//    void createProjectWithoutName() {
//
//        ProjectRequestDTO projectRequestDto = ProjectRequestDTO.builder()
//                .description("Test description")
//                .build();
//
//        Throwable exception = assertThrows(HttpClientErrorException.class, () -> {
//            restTemplate.postForEntity(
//                    new URI(BASE_URL + "/projects"),
//                    projectRequestDto,
//                    ProjectResponseDTO.class
//            );
//        });
//
//        assertEquals(HttpStatus.BAD_REQUEST, ((HttpClientErrorException) exception).getStatusCode());
//    } //Expected HttpClientErrorException, but nothing was thrown
//
//    @Test
//    @Order(5)
//    void createTaskWithoutTaskName() throws Exception {
//        TaskRequestDTO taskRequestDto = TaskRequestDTO.builder()
//                .description("Negative testing")
//                .projectId(project.getProjectId())
//                .build();
//
//        URI uri = URI.create(BASE_URL + "/tasks");
//
//        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () ->
//                restTemplate.postForEntity(uri, taskRequestDto, TaskResponseDTO.class));
//
//        assertEquals(HttpStatus.BAD_REQUEST, exception.getMessage());
//    } // Expected HttpClientErrorException but was ResourceAccessException
//
//    @Test
//    @Order(6)
//    void updateTask() {
//
//        TaskRequestDTO updatedTaskRequestDto = TaskRequestDTO.builder()
//                .taskName("Updated test task Name")
//                .description("Updated test description")
//                .projectId(project.getProjectId())
//                .build();
//
//
//        assertDoesNotThrow(() -> {
//            ResponseEntity<TaskResponseDTO> updatedTaskDtoResponseEntity = restTemplate.exchange(
//                    new URI(BASE_URL + "/tasks/" + task.getTaskId()),
//                    HttpMethod.PUT,
//                    new HttpEntity<>(updatedTaskRequestDto),
//                    TaskResponseDTO.class);
//
//            System.out.println(updatedTaskDtoResponseEntity);
//            assertNotNull(updatedTaskDtoResponseEntity);
//            assertEquals(HttpStatus.OK, updatedTaskDtoResponseEntity.getStatusCode());
//            assertNotNull(updatedTaskDtoResponseEntity.getBody());
//
//
//        });
//    }
//
//    @Test()
//    @Order(7)
//    void updateTaskWithNonExistentId() throws Exception {
//
//        RestTemplate restTemplate = new RestTemplate();
//        TaskRequestDTO invalidTaskRequestDto = TaskRequestDTO.builder()
//                .taskName("Updated test task Name")
//                .description("Updated test description")
//                .projectId(project.getProjectId())
//                .build();
//
//        Long invalidTaskId = Long.MAX_VALUE;
//
//        try {
//            ResponseEntity<TaskResponseDTO> invalidTaskDtoResponseEntity = restTemplate.exchange(
//                    new URI(BASE_URL + "/tasks/" + invalidTaskId),
//                    HttpMethod.PUT,
//                    new HttpEntity<>(invalidTaskRequestDto),
//                    TaskResponseDTO.class);
//
//            fail("Expected HttpClientErrorException, but the request succeeded");
//        } catch (HttpClientErrorException e) {
//            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
//        }
//    }
//
//    @Test
//    void getTaskWithProjectIdTaskId() throws URISyntaxException {
//
//        URI uri = new URI(BASE_URL + "/tasks" + "/project/" + project.getProjectId() + "/task/" + task.getTaskId());
//
//        ResponseEntity<TaskResponseDTO> responseEntity = restTemplate.getForEntity(uri, TaskResponseDTO.class);
//
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertNotNull(responseEntity.getBody());
//    } // Expected: 200 OK Actual: 404 NOT_FOUND, Always: "Проект не найден"
//
//    @Test
//    void getTaskWithProjectIdAndInvalidTaskId() {
//        Long invalidTaskId = Long.MAX_VALUE;
//
//        URI uri = URI.create(BASE_URL + "/tasks" + "/project/" + project.getProjectId() + "/task/" + invalidTaskId);
//
//        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () ->
//                restTemplate.getForEntity(uri, TaskResponseDTO.class));
//
//        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
//    } // Expected HttpClientErrorException to be thrown, but nothing was thrown
//
//
//    @Test
//    void getTask() throws Exception {
//
//        String url = UriComponentsBuilder.fromHttpUrl(String.format("http://localhost:%d/tasks/project/{projectId}/tasks", port))
//                .queryParam("from", 0)
//                .queryParam("size", 10)
//                .buildAndExpand(project.getProjectId())
//                .toUriString();
//
//
//        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
//
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//
//        String responseBody = responseEntity.getBody();
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.readTree(responseBody);
//    }
//
//    @Test
//    void getTasksFromInvalidProject() throws Exception {
//        Long invalidProjectId = Long.MAX_VALUE;
//
//        String url = UriComponentsBuilder.fromHttpUrl(String.format("http://localhost:%d/tasks/project/{projectId}/tasks", port))
//                .queryParam("from", 0)
//                .queryParam("size", 10)
//                .buildAndExpand(invalidProjectId)
//                .toUriString();
//
//        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
//
//        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
//    }
//
//    @Test
//    void getTasksWithInvalidProjectId() {
//        Long invalidProjectId = Long.MAX_VALUE;
//
//        ResponseEntity<Map<String, String>> responseEntity = restTemplate.exchange(
//                BASE_URL + "/tasks/project/{projectId}/tasks?from=0&size=10",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<>() {
//                },
//                invalidProjectId);
//
//        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
//
//        Map<String, String> errorResponse = responseEntity.getBody();
//        assertNotNull(errorResponse);
//        assertEquals("Проект не найден", errorResponse.get("error"));
//    }
//
//
//    @Test
//    void getChildTasks() throws Exception {
//
//        Long parentTaskId = 15L;
//
//        String url = UriComponentsBuilder.fromHttpUrl(String.format("http://localhost:%d/tasks/{parentTaskId}/child-tasks", port))
//                .queryParam("from", 0)
//                .queryParam("size", 10)
//                .buildAndExpand(parentTaskId)
//                .toUriString();
//
//        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
//
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertNotNull(responseEntity.getBody());
//
//    }
//
//    @Test
//    void getChildTasksWithNonExistentParentId() throws Exception {
//        Long invalidParentTaskId = Long.MAX_VALUE;
//        Integer from = 0;
//        Integer size = 10;
//
//        assertThrows(org.springframework.web.client.RestClientException.class, () -> {
//            restTemplate.exchange(
//                    BASE_URL + "/tasks/{parentTaskId}/child-tasks?from={from}&size={size}",
//                    HttpMethod.GET,
//                    null,
//                    new ParameterizedTypeReference<List<TaskResponseDTO>>() {
//                    },
//                    invalidParentTaskId, from, size);
//        });
//    }
//
//    @Test
//    @Order(9)
//    void deleteTask() throws URISyntaxException {
//        assertNotNull(task);
//
//        URI uri = UriComponentsBuilder.fromUriString(BASE_URL)
//                .path("/tasks/{taskId}")
//                .buildAndExpand(task.getTaskId())
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
//    @Test
//    @Order(10)
//    void deleteNonExistentTask() throws URISyntaxException {
//        Long invalidTaskId = Long.MAX_VALUE;
//
//        ResponseEntity<Void> responseEntity = restTemplate.exchange(
//                new URI(BASE_URL + "/tasks/" + invalidTaskId),
//                HttpMethod.DELETE,
//                null,
//                Void.class);
//
//        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
//        assertThrows(TaskNotFoundException.class, () ->
//                taskService.get(project.getProjectId(), invalidTaskId));
//    } // Expected TaskNotFoundException but was ProjectNotFoundException
//}