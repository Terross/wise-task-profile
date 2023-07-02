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

@Component
@RequiredArgsConstructor
public class UpdateProfileOperation {

    private final ProfileMapper profileMapper;
    private final ProfileRepository profileRepository;

    public UpdateProfileResponse activate(Profile profile) {
        var profileEntity = profileMapper.toProfileEntity(profile);
        profileRepository.findById(profileEntity.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.PROFILE_NOT_FOUND));
        profileRepository.save(profileEntity);

        return UpdateProfileResponse.newBuilder()
                .setProfile(profileMapper.toProfile(profileEntity))
                .build();
    }
}
