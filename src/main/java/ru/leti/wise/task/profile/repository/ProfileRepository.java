package ru.leti.wise.task.profile.repository;

import org.springframework.data.repository.CrudRepository;
import ru.leti.wise.task.profile.model.ProfileEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProfileRepository extends CrudRepository<ProfileEntity, UUID> {

    Optional<ProfileEntity> findByEmail(String email);
    List<ProfileEntity> findAll();
}
