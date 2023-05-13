package ru.leti.wise.task.profile.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

import static jakarta.persistence.EnumType.STRING;

@Data
@Entity
@Table(name = "profile")
public class ProfileEntity {

    @Id
    private UUID id;

    private String email;

    @Column(name = "profile_password")
    private String profilePassword;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String patronymic;

    @Column(name = "student_group")
    private String studentGroup;

    @Column(name = "student_course")
    private Integer studentCourse;

    @Enumerated(STRING)
    @Column(name = "profile_role")
    private Role profileRole;

}
