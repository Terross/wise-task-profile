package ru.leti.wise.task.profile.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.leti.wise.task.profile.model.ProfileEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends CrudRepository<ProfileEntity, UUID> {

    Optional<ProfileEntity> findByEmail(String email);

    List<ProfileEntity> findAll();

    @Modifying
    @Query(value = """
            UPDATE wise_task_profile.profile p
            SET email = :#{#profile.email},
                first_name = :#{#profile.firstName},
                last_name = :#{#profile.lastName},
                patronymic = :#{#profile.patronymic},
                student_group = :#{#profile.studentGroup},
                student_course = :#{#profile.studentCourse},
                profile_role = :#{#profile.profileRole.toString()}
            WHERE id = :#{#profile.id}
            """, nativeQuery = true)
    int updateProfileBy(ProfileEntity profile);
}
