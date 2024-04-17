package kg.nurtelecom.internlabs.taskmanager.repository;

import kg.nurtelecom.internlabs.taskmanager.model.VerificationToken;
import kg.nurtelecom.internlabs.taskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken getByCode(String verificationCode);

    VerificationToken findByUser(User user);
}
