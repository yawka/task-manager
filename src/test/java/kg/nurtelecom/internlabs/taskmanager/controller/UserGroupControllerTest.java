//package kg.nurtelecom.internlabs.taskmanager.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import kg.nurtelecom.internlabs.taskmanager.payload.usergroupdto.UserGroupResponseDTO;
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
//import org.springframework.web.util.UriComponentsBuilder;
//
//import javax.annotation.PostConstruct;
//
//import java.net.URI;
//import java.util.List;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.assertj.core.api.Assertions.assertThat;
//
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//class UserGroupControllerTest {
//
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//
//    private UserGroupResponseDTO userGroupResponseDTO;
//    String BASE_URL;
//
//    @PostConstruct
//    void initUrl() {
//        BASE_URL = "http://localhost:" + port + "/users/groups";
//    }
//
//    @Test
//    void getAllUserGroups() throws Exception {
//        String url = UriComponentsBuilder.fromHttpUrl(String.format("http://localhost:%d/users/groups", port))
//                .queryParam("from", 0)
//                .queryParam("size", 10)
//                .buildAndExpand(userGroupResponseDTO.getUserGroupId())
//                .toUriString();
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
//
//    @Test
//    @Order(2)
//    void getUserGroupById() {
//        assertDoesNotThrow(() -> {
//
//            assertNotNull(userGroupResponseDTO.getUserGroupId());
//            ResponseEntity<UserGroupResponseDTO> forEntity = restTemplate.getForEntity(new URI(BASE_URL + "/" + userGroupResponseDTO.getUserGroupId()), UserGroupResponseDTO.class);
//            assertNotNull(forEntity);
//            assertEquals(HttpStatus.OK, forEntity.getStatusCode());
//            assertNotNull(forEntity.getBody());
//            UserGroupResponseDTO body = forEntity.getBody();
//
//            assertNotNull(body.getUserGroupId());
//            assertEquals(userGroupResponseDTO.getGroupName(), body.getGroupName());
//
//
//        });
//    }
//
//    @Test
//    void getUserGroupByInvalidId() {
//        Long invalidUserGroupId = Long.MAX_VALUE;
//
//        String url = UriComponentsBuilder.fromHttpUrl(String.format(BASE_URL + "/{userGroupId}"))
//                .buildAndExpand(invalidUserGroupId)
//                .toUriString();
//
//        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
//
//        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
//
//        String expectedResponseBody = "{\"error\":\"Группа с таким id не найдена!\"}";
//        String actualResponseBody = responseEntity.getBody();
//
//        assertEquals(expectedResponseBody, actualResponseBody);
//    }
//
//    @Test
//    @Order(1)
//    void createUserGroup() {
//        userGroupResponseDTO = UserGroupResponseDTO.builder()
//                .groupName("QA")
//                .build();
//
//        assertDoesNotThrow(() -> {
//            ResponseEntity<UserGroupResponseDTO> userGroupResponseDTOResponseEntity = restTemplate.postForEntity(new URI(BASE_URL), userGroupResponseDTO, UserGroupResponseDTO.class);
//
//            assertNotNull(userGroupResponseDTO);
//            assertEquals(HttpStatus.CREATED, userGroupResponseDTOResponseEntity.getStatusCode());
//            assertNotNull(userGroupResponseDTOResponseEntity.getBody());
//            UserGroupResponseDTO body = userGroupResponseDTOResponseEntity.getBody();
//            userGroupResponseDTO = body;
//            assertNotNull(body.getUserGroupId());
//            assertEquals(userGroupResponseDTO.getGroupName(), body.getGroupName());
//        });
//    }
//
//
//    @Test
//    @Order(3)
//    void updateUserGroup() {
//        UserGroupResponseDTO updatedUserGroupResponseDTO = UserGroupResponseDTO.builder()
//                .groupName("Updated Group Name")
//                .build();
//
//        assertDoesNotThrow(() -> {
//            ResponseEntity<UserGroupResponseDTO> updatedUserGroupResponseDTOResponseEntity = restTemplate.exchange(
//                    new URI(BASE_URL + "/" + userGroupResponseDTO.getUserGroupId()),
//                    HttpMethod.PUT,
//                    new HttpEntity<>(updatedUserGroupResponseDTO),
//                    UserGroupResponseDTO.class);
//
//            System.out.println(updatedUserGroupResponseDTOResponseEntity);
//            assertNotNull(updatedUserGroupResponseDTOResponseEntity);
//            assertEquals(HttpStatus.OK, updatedUserGroupResponseDTOResponseEntity.getStatusCode());
//            assertNotNull(updatedUserGroupResponseDTOResponseEntity.getBody());
//        });
//    }
//
//    @Test
//    void updateUserGroupWithNonExistentId() throws Exception {
//        Long invalidUserGroupId = Long.MAX_VALUE;
//
//        UserGroupResponseDTO invalidUserGroupResponseDTO = UserGroupResponseDTO.builder()
//                .groupName("New User Group Name")
//                .build();
//
//        try {
//            ResponseEntity<UserGroupResponseDTO> invalidUserGroupDTOResponseEntity = restTemplate.exchange(
//                    new URI(BASE_URL + "/" + invalidUserGroupId),
//                    HttpMethod.PUT,
//                    new HttpEntity<>(invalidUserGroupResponseDTO),
//                    UserGroupResponseDTO.class);
//            System.out.println("Response: " + invalidUserGroupDTOResponseEntity.toString());
//
//            assertEquals(HttpStatus.NOT_FOUND, invalidUserGroupDTOResponseEntity.getStatusCode());
//        } catch (HttpClientErrorException e) {
//            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
//        } catch (Exception e) {
//        }
//    }
//
//
//    @Test
//    @Order(4)
//    void deleteUserGroup() {
//        assertNotNull(userGroupResponseDTO);
//
//        URI uri = UriComponentsBuilder.fromUriString(BASE_URL)
//                .path("/{userGroupId}")
//                .buildAndExpand(userGroupResponseDTO.getUserGroupId())
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
//    void deleteUserGroupWithNonExistingId() {
//        Long invalidUserGroupId = Long.MAX_VALUE;
//
//        URI uri = UriComponentsBuilder.fromUriString(BASE_URL)
//                .path("/{userGroupId}")
//                .buildAndExpand(invalidUserGroupId)
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
//    @Test
//    @Order(10)
//    void createUserGroupWithEmptyGroupName() {
//
//        userGroupResponseDTO = UserGroupResponseDTO.builder()
//                .groupName("")
//                .build();
//
//        Throwable exception = assertThrows(HttpClientErrorException.class, () -> {
//            restTemplate.postForEntity(
//                    new URI(BASE_URL),
//                    userGroupResponseDTO,
//                    UserGroupResponseDTO.class
//            );
//        });
//
//        ResponseEntity<String> responseEntity = restTemplate.getForEntity(BASE_URL, String.class);
//
//        assertEquals(HttpStatus.BAD_REQUEST, ((HttpClientErrorException) exception).getStatusCode());
//
//        String expectedResponseBody = "{\"error\":\"Имя группы не должно быть пустым\"}";
//        String actualResponseBody = responseEntity.getBody();
//
//        assertEquals(expectedResponseBody, actualResponseBody);
//    } // Expected HttpClientErrorException to be thrown, but nothing was thrown
//
//
//    @Test()
//    void returnUserGroupsForSecondPage() {
//        String url = UriComponentsBuilder.fromHttpUrl(String.format("http://localhost:%d/users/groups", port))
//                .queryParam("page", 1)
//                .queryParam("size", 10)
//                .queryParam("sort", "userGroupId,asc")
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
//        System.out.println("Response Body:");
//        for (Map<String, Object> item : responseEntity.getBody()) {
//            System.out.println(item);
//        }
//    }
//
//    @Test()
//    void returnBadRequestForInvalidEndpoint() {
//        String url = UriComponentsBuilder.fromHttpUrl(String.format("http://localhost:%d/users/group", port))
//                .queryParam("page", 1)
//                .queryParam("size", 10)
//                .queryParam("sort", "userGroupId,asc")
//                .toUriString();
//
//
//        ResponseEntity<String> responseEntity = restTemplate.exchange(
//                url,
//                HttpMethod.GET,
//                null,
//                String.class
//        );
//
//        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//        assertThat(responseEntity.getBody()).isNotNull();
//
//    }
//}