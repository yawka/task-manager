package kg.nurtelecom.internlabs.taskmanager.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String code;
    @OneToOne(fetch = LAZY)
    private User user;
    private Date expiryDate;
}
