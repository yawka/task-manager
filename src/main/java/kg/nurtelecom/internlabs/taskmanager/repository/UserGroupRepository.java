package kg.nurtelecom.internlabs.taskmanager.repository;

import kg.nurtelecom.internlabs.taskmanager.model.UserGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
}
