package ru.leti.wise.task.profile.logic;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.leti.wise.task.profile.ProfileGrpc.UpdateProfileResponse;
import ru.leti.wise.task.profile.ProfileOuterClass.Profile;
import ru.leti.wise.task.profile.error.ProfileNotFoundException;
import ru.leti.wise.task.profile.mapper.ProfileMapper;
import ru.leti.wise.task.profile.repository.ProfileRepository;

@Component
@RequiredArgsConstructor
public class UpdateProfileOperation {

    private final ProfileMapper profileMapper;
    private final ProfileRepository profileRepository;

    @Transactional
    public UpdateProfileResponse activate(Profile profile) {
        var profileEntity = profileMapper.toProfileEntity(profile);
        int updatedRow = profileRepository.updateProfileBy(profileEntity);
        if (updatedRow < 1) {
            throw new ProfileNotFoundException();
        }

        return UpdateProfileResponse.newBuilder()
                .setProfile(profileMapper.toProfile(profileEntity))
                .build();
    }
}
