package ru.leti.wise.task.profile.logic;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import ru.leti.wise.task.profile.ProfileGrpc;
import ru.leti.wise.task.profile.ProfileGrpc.ResetPasswordRequest;
import ru.leti.wise.task.profile.error.BusinessException;
import ru.leti.wise.task.profile.error.ErrorCode;
import ru.leti.wise.task.profile.mapper.ProfileMapper;
import ru.leti.wise.task.profile.model.PasswordRecoveryEntity;
import ru.leti.wise.task.profile.model.ProfileEntity;
import ru.leti.wise.task.profile.repository.PasswordRecoveryRepository;
import ru.leti.wise.task.profile.repository.ProfileRepository;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ResetPasswordOperation {

    private final ProfileRepository profileRepository;
    private final ProfileMapper profileMapper;
    private final PasswordRecoveryRepository passwordRecoveryRepository;

    public ProfileGrpc.ResetPasswordResponse activate(ResetPasswordRequest resetPasswordRequest) {
        PasswordRecoveryEntity passwordRecoveryEntity = passwordRecoveryRepository
                .findFirstByRecoveryTokenOrderByExpiresAtDesc(UUID.fromString(resetPasswordRequest.getRecoveryToken()))
                .orElseThrow(() -> new BusinessException(ErrorCode.UNKNOWN_LINK));

        ProfileEntity profile = profileRepository.findByEmail(passwordRecoveryEntity.getEmail()).orElseThrow(
                () -> new BusinessException(ErrorCode.PROFILE_NOT_FOUND));

        profile.setProfilePassword(BCrypt.hashpw(resetPasswordRequest.getNewPassword(), BCrypt.gensalt()));
        profileRepository.save(profile);
        passwordRecoveryRepository.delete(passwordRecoveryEntity);
        return ProfileGrpc.ResetPasswordResponse.newBuilder()
                .setProfile(profileMapper.toProfile(profile))
                .build();
    }
}

