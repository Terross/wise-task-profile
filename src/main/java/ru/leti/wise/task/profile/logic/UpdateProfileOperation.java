package ru.leti.wise.task.profile.logic;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.leti.wise.task.profile.ProfileGrpc.UpdateProfileResponse;
import ru.leti.wise.task.profile.ProfileOuterClass.Profile;
import ru.leti.wise.task.profile.error.BusinessException;
import ru.leti.wise.task.profile.error.ErrorCode;
import ru.leti.wise.task.profile.mapper.ProfileMapper;
import ru.leti.wise.task.profile.repository.ProfileRepository;
import ru.leti.wise.task.profile.validation.ProfileValidator;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateProfileOperation {

    private final ProfileMapper profileMapper;
    private final ProfileRepository profileRepository;
    private final ProfileValidator profileValidator;

    public UpdateProfileResponse activate(Profile profile) {
        profileValidator.checkEmptyFieldsForUpdate(profile);
        profileValidator.checkUniqueEmail(profile);
        var oldProfile = profileValidator.checkForExistence(profile.getId());
        profileMapper.updateProfileEntity(oldProfile, profile);
        profileRepository.save(oldProfile);

        return UpdateProfileResponse.newBuilder()
                .setProfile(profileMapper.toProfile(oldProfile))
                .build();
    }
}
