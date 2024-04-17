package kg.nurtelecom.internlabs.taskmanager.model;

import kg.nurtelecom.internlabs.taskmanager.model.enums.Roles;
import lombok.Getter;

import jakarta.persistence.*;

@Entity
@Getter
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Roles name;

    public Role() {

    }
}
