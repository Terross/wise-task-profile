package ru.leti.wise.task.profile.repository;

import io.micrometer.observation.annotation.Observed;
import org.springframework.data.repository.CrudRepository;
import ru.leti.wise.task.profile.model.PasswordRecoveryEntity;

import java.util.Optional;
import java.util.UUID;


@Observed
public interface PasswordRecoveryRepository extends CrudRepository<PasswordRecoveryEntity, UUID> {


    Optional<PasswordRecoveryEntity> findFirstByRecoveryTokenOrderByExpiresAtDesc(UUID recoveryToken);
    void deleteAllByEmail(String email);

}
