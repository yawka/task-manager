package kg.nurtelecom.internlabs.taskmanager.repository;

import kg.nurtelecom.internlabs.taskmanager.model.RefreshToken;
import kg.nurtelecom.internlabs.taskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    @Modifying
    int deleteByUser(User user);
}
