package kg.nurtelecom.internlabs.taskmanager.repository;

import kg.nurtelecom.internlabs.taskmanager.model.Role;
import kg.nurtelecom.internlabs.taskmanager.model.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(Roles name);

}
