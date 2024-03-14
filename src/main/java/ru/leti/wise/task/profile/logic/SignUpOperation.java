package ru.leti.wise.task.profile.logic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.leti.wise.task.profile.ProfileGrpc.SignUpRequest;
import ru.leti.wise.task.profile.ProfileGrpc.SignUpResponse;
import ru.leti.wise.task.profile.mapper.ProfileMapper;
import ru.leti.wise.task.profile.model.ProfileEntity;
import ru.leti.wise.task.profile.repository.ProfileRepository;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SignUpOperation {

    private final ProfileMapper profileMapper;
    private final ProfileRepository profileRepository;

    public SignUpResponse activate(SignUpRequest signUpRequest) {
        ProfileEntity profile = profileMapper.toProfileEntity(signUpRequest.getProfile());
        profile.setId(UUID.randomUUID());
        profileRepository.save(profile);
        return SignUpResponse.newBuilder()
                .setProfile(profileMapper.toProfile(profile))
                .build();
    }
}
