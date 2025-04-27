package ru.leti.wise.task.profile.logic;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import ru.leti.wise.task.profile.ProfileGrpc.SignUpRequest;
import ru.leti.wise.task.profile.ProfileGrpc.SignUpResponse;
import ru.leti.wise.task.profile.error.BusinessException;
import ru.leti.wise.task.profile.error.ErrorCode;
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
        if (!profileRepository.findByEmail(signUpRequest.getProfile().getEmail()).isEmpty()){
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_TAKEN);
        }
        ProfileEntity profile = profileMapper.toProfileEntity(signUpRequest.getProfile());
        profile.setId(UUID.randomUUID());
        profile.setProfilePassword(BCrypt.hashpw(profile.getProfilePassword(), BCrypt.gensalt()));
        profileRepository.save(profile);
        return SignUpResponse.newBuilder()
                .setProfile(profileMapper.toProfile(profile))
                .build();
    }
}
