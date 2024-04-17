    package kg.nurtelecom.internlabs.taskmanager.repository;


    import kg.nurtelecom.internlabs.taskmanager.model.User;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;

    import java.util.Optional;

    public interface UserRepository extends JpaRepository<User, Long> {

        Optional<User> findByEmailIgnoreCase(String email);

        Optional<User> findByUsernameIgnoreCase(String username);

        User findByUsernameOrEmail(String username, String email);

        Optional<User> findByUsername(String name);

        Boolean existsByUsername(String username);

        Boolean existsByEmail(String email);

    }
