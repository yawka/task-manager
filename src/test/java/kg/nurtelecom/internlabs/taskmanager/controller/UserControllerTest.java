//package kg.nurtelecom.internlabs.taskmanager.controller;
//
//import kg.nurtelecom.internlabs.taskmanager.payload.userdto.UserResponseDTO;
//import org.junit.jupiter.api.*;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.*;
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
//class UserControllerTest {
//
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    private UserResponseDTO userResponseDTO;
//
//    String BASE_URL;
//
//    @PostConstruct
//    void initURL() {
//        BASE_URL = "http://localhost:" + port + "/users";
//    }
//
//    private List<Long> generatedUserIds = new ArrayList<>();
//
//
//    @ParameterizedTest
//    @DisplayName("Create users with different types of name and with Boundary values (name - 19, 20 symbols, email, position - 29, 30")
//    @CsvSource({
//            "Test User, test@mail.com, QA",
//            "Test User Test User, testtesttesttesttest@mail.com, QA QA QA QA QA QA QA QA QA QA",
//            "Test User Test User2, testtesttesttesttest@maill.com, QA QA QA QA QA QA QA QA QA QA1",
//            "1 User, test@g.r, Dev",
//            "1 User, test@g, Dev",
//            "!@$%, t@gmail.de, tester-jun",
//            " User-User Lastname, 123@j.us,  manager"
//
//    })
//    @Order(1)
//    void createUser(String username, String email, String position) {
//        userResponseDTO = UserResponseDTO.builder()
//                .username(username)
//                .email(email)
//                .position(position)
//                .build();
//
//        assertDoesNotThrow(() -> {
//            ResponseEntity<UserResponseDTO> userResponseDTOResponseEntity = restTemplate.postForEntity(new URI(BASE_URL), userResponseDTO, UserResponseDTO.class);
//
//            assertNotNull(userResponseDTOResponseEntity);
//            assertEquals(HttpStatus.CREATED, userResponseDTOResponseEntity.getStatusCode());
//            assertNotNull(userResponseDTOResponseEntity.getBody());
//            UserResponseDTO body = userResponseDTOResponseEntity.getBody();
//            userResponseDTO = body;
//            assertNotNull(body.getUserId());
//            assertEquals(userResponseDTO.getUsername(), body.getUsername());
//            assertEquals(userResponseDTO.getEmail(), body.getEmail());
//            assertEquals(userResponseDTO.getPosition(), body.getPosition());
//
//            generatedUserIds.add(body.getUserId());
//        });
//    }
//
//    @ParameterizedTest
//    @DisplayName("Create Users with invalid values and username = 21 symbols, email, position - 31")
//    @MethodSource("provideInvalidUserValues")
//    void createUsersWithInvalidValues(String username, String email, String position) throws URISyntaxException {
//        ResponseEntity<UserResponseDTO> responseEntity = restTemplate.postForEntity(
//                new URI(BASE_URL),
//                UserResponseDTO.builder()
//                        .username(username)
//                        .email(email)
//                        .position(position)
//                        .build(),
//                UserResponseDTO.class
//        );
//        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
//    }
//
//    Stream<Arguments> provideInvalidUserValues() {
//        return Stream.of(
//                Arguments.of("Test User Test User21", "test@mail.de", "Tester"),
//                Arguments.of("New user", "testtesttesttesttest@maill.comm", "QA"),
//                Arguments.of("Username LastName", "test@mail.de", "QA QA QA QA QA QA QA QA QA QA31"),
//                Arguments.of("Invalid type of email", "mail.de", "dev"),
//                Arguments.of("Invalid type of email", "mail@", "dev"),
//                Arguments.of(" ", "test@mail.de", "QA"),
//                Arguments.of("Test User", " ", "Tester"),
//                Arguments.of("Test User", "test@mail.de", " "),
//                Arguments.of(null, null, null),
//                Arguments.of(null, "test@mail.de", "Tester"),
//                Arguments.of("Test User", null, "Tester"),
//                Arguments.of("Test User", "test@mail.de", null)
//
//        );
//    }
//
//
//    @Test
//    @DisplayName("Show user by Id")
//    void getUser() {
//        assertDoesNotThrow(() -> {
//
//            assertNotNull(userResponseDTO.getUserId());
//            ResponseEntity<UserResponseDTO> forEntity = restTemplate.getForEntity(new URI(BASE_URL + "/" + userResponseDTO.getUserId()), UserResponseDTO.class);
//            assertNotNull(forEntity);
//            assertEquals(HttpStatus.OK, forEntity.getStatusCode());
//            assertNotNull(forEntity.getBody());
//            UserResponseDTO body = forEntity.getBody();
//
//            assertNotNull(body.getUserId());
//            assertEquals(userResponseDTO.getUsername(), body.getUsername());
//            assertEquals(userResponseDTO.getEmail(), body.getEmail());
//            assertEquals(userResponseDTO.getPosition(), body.getPosition());
//
//        });
//    }
//
//    @Test
//    void getUserWithInvalidId() throws Exception {
//        Long invalidUserId = Long.MAX_VALUE;
//
//        String url = UriComponentsBuilder.fromHttpUrl(String.format(BASE_URL + "/{userId}"))
//                .buildAndExpand(invalidUserId)
//                .toUriString();
//
//        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
//
//        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
//
//    }
//
//    @ParameterizedTest
//    @NullAndEmptySource
//    @DisplayName("Update the user by changing different parameters")
//    @CsvSource({
//            "Elon Musk, {email}, {position}",
//            "{username}, t@gmail.us, {position}",
//            "{username}, {email}, Middle Dev",
//            "Steve Jobs, st@gmail.us, {position}",
//            "Larry Page, {email}, CEO",
//            "{username}, te@gm.us, Senior QA",
//            "Test User, test@mail.com, QA",
//            "Test User Test User, testtesttesttesttest@mail.com, QA QA QA QA QA QA QA QA QA QA",
//            "Test User Test User2, testtesttesttesttest@maill.com, QA QA QA QA QA QA QA QA QA QA1",
//            "1 User, test@g.r, Dev",
//            "1 User, test@g, Dev",
//            "!@$%, t@gmail.de, tester-jun",
//            " User-User Lastname, 123@j.us,  manager"
//
//    })
//
//    @Order(4)
//    void updateUserWithValidValues(String username, String email, String position) {
//
//
//        username = username.equals("{username}") ? userResponseDTO.getUsername() : username;
//        email = email.equals("{email}") ? userResponseDTO.getEmail() : email;
//        position = position.equals("{position}") ? userResponseDTO.getPosition() : position;
//
//
//        UserResponseDTO updatedUserResponseDTO = UserResponseDTO.builder()
//                .username(username)
//                .email(email)
//                .position(position)
//                .build();
//
//        assertDoesNotThrow(() -> {
//            ResponseEntity<UserResponseDTO> updatedUserDTOResponseEntity = restTemplate.exchange(
//                    new URI(BASE_URL + "/" + userResponseDTO.getUserId()),
//                    HttpMethod.PUT,
//                    new HttpEntity<>(updatedUserResponseDTO),
//                    UserResponseDTO.class);
//
//            System.out.println(updatedUserDTOResponseEntity);
//            assertNotNull(updatedUserDTOResponseEntity);
//            assertEquals(HttpStatus.OK, updatedUserDTOResponseEntity.getStatusCode());
//            assertNotNull(updatedUserDTOResponseEntity.getBody());
//        });
//    }
//
//    @ParameterizedTest
//    @DisplayName("Update Users with null values")
//    @MethodSource("provideInvalidUserValuesForUpdate")
//    void updateUsersWithNullValues(String username, String email, String position) throws URISyntaxException {
//
//        ResponseEntity<UserResponseDTO> updatedUserDTOResponseEntity = restTemplate.exchange(
//                UriComponentsBuilder.fromHttpUrl(BASE_URL)
//                        .path("/{userId}")
//                        .buildAndExpand(userResponseDTO.getUserId())
//                        .toUri(),
//                HttpMethod.PUT,
//                new HttpEntity<>(UserResponseDTO.builder()
//                        .username(username)
//                        .email(email)
//                        .position(position)
//                        .build()),
//                UserResponseDTO.class
//        );
//
//        System.out.println(updatedUserDTOResponseEntity);
//        assertEquals(HttpStatus.BAD_REQUEST, updatedUserDTOResponseEntity.getStatusCode());
//    }
//
//    Stream<Arguments> provideInvalidUserValuesForUpdate() {
//        return Stream.of(
//                Arguments.of(" ", "test@mail.de", "QA"),
//                Arguments.of("Test User", " ", "Tester"),
//                Arguments.of("Test User", "test@mail.de", " "),
//                Arguments.of(null, null, null),
//                Arguments.of(null, "test@mail.de", "Tester"),
//                Arguments.of("Test User", null, "Tester"),
//                Arguments.of("Test User", "test@mail.de", null)
//
//        );
//    }
//
//    @Test
//    void updateUserWithNonExistentId() throws Exception {
//        Long invalidUserId = Long.MAX_VALUE;
//
//        UserResponseDTO invalidUserResponseDTO = UserResponseDTO.builder()
//                .username("New Username")
//                .email("new@mail.com")
//                .position("New position")
//                .build();
//
//        try {
//            ResponseEntity<UserResponseDTO> invalidUserDTOResponseEntity = restTemplate.exchange(
//                    new URI(BASE_URL + "/" + invalidUserId),
//                    HttpMethod.PUT,
//                    new HttpEntity<>(invalidUserResponseDTO),
//                    UserResponseDTO.class);
//            System.out.println("Response: " + invalidUserDTOResponseEntity.toString());
//
//            assertEquals(HttpStatus.NOT_FOUND, invalidUserDTOResponseEntity.getStatusCode());
//        } catch (HttpClientErrorException e) {
//            assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
//        } catch (Exception e) {
//        }
//    }
//
//
//    @ParameterizedTest
//    @MethodSource("provideUserIds")
//    void deleteUser(Long userId) {
//        assertNotNull(userResponseDTO);
//
//        URI uri = UriComponentsBuilder.fromHttpUrl(BASE_URL)
//                .path("/{userId}")
//                .buildAndExpand(userId)
//                .toUri();
//
//        ResponseEntity<Void> responseEntity = restTemplate.exchange(
//                uri,
//                HttpMethod.DELETE,
//                null,
//                Void.class);
//
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//    }
//
//    Stream<Long> provideUserIds() {
//        return generatedUserIds.stream();
//    }
//
//    @Test
//    @Order(11)
//    void deleteUserWithNonExistingId() {
//        Long invalidUserId = Long.MAX_VALUE;
//
//        URI uri = UriComponentsBuilder.fromUriString(BASE_URL)
//                .path("/{userId}")
//                .buildAndExpand(invalidUserId)
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
//    @DisplayName("Show users on different pages")
//    @ValueSource(ints = {0, 1, 2})
//    void returnUsers(int pageNumber) {
//        String url = UriComponentsBuilder.fromHttpUrl(String.format("http://localhost:%d/users", port))
//                .queryParam("page", pageNumber)
//                .queryParam("size", 10)
//                .queryParam("sort", "userId,asc")
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
//    @DisplayName("Show users with invalid page numbers and sizes")
//    @CsvSource({
//            "-1, 10",
//            "0, 0",
//            "1, -5",
//            "2, 1000"
//    })
//    void returnUsersWithInvalidParams(int invalidPageNumber, int invalidSize) {
//        String url = UriComponentsBuilder.fromHttpUrl(String.format("http://localhost:%d/users", port))
//                .queryParam("page", invalidPageNumber)
//                .queryParam("size", invalidSize)
//                .queryParam("sort", "userId,asc")
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
//
//    @ParameterizedTest
//    @DisplayName("Show different number of users on the page")
//    @ValueSource(ints = {2, 5, 10})
//    void returnUsersWithDifferentPageSizes(int userSize) {
//        String url = UriComponentsBuilder.fromHttpUrl(String.format("http://localhost:%d/users", port))
//                .queryParam("page", 0)
//                .queryParam("size", userSize)
//                .queryParam("sort", "userId,asc")
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
//        System.out.println("Response Body for" + userSize + "users");
//        for (Map<String, Object> item : responseEntity.getBody()) {
//            System.out.println(item);
//        }
//    }
//
//
//    @ParameterizedTest
//    @DisplayName("Show invalid number of users")
//    @ValueSource(ints = {-1, 0})
//    void returnUsersWithInvalidNumber(int invalidNumberOfUsers) {
//        String url = UriComponentsBuilder.fromHttpUrl(String.format("http://localhost:%d/users", port))
//                .queryParam("page", 0)
//                .queryParam("size", invalidNumberOfUsers)
//                .queryParam("sort", "userId,asc")
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
//}