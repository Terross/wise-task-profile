package ru.leti.wise.task.profile.logic;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;
import ru.leti.wise.task.profile.ProfileGrpc.SignInRequest;
import ru.leti.wise.task.profile.ProfileGrpc.SignInResponse;
import ru.leti.wise.task.profile.error.BusinessException;
import ru.leti.wise.task.profile.error.ErrorCode;
import ru.leti.wise.task.profile.mapper.ProfileMapper;
import ru.leti.wise.task.profile.model.ProfileEntity;
import ru.leti.wise.task.profile.repository.ProfileRepository;
import ru.leti.wise.task.profile.validation.ProfileValidator;

@Component
@RequiredArgsConstructor
public class SignInOperation {

    private final ProfileMapper profileMapper;
    private final ProfileValidator profileValidator;
    public SignInResponse activate(SignInRequest request) {

        ProfileEntity profile = profileValidator.checkEmailExistence(request.getEmail());

        if (request.getPassword().isBlank() || !BCrypt.checkpw(request.getPassword(),profile.getProfilePassword())) {
            throw new BusinessException(ErrorCode.INVALID_PASSWORD);
        }

        return SignInResponse.newBuilder()
                .setProfile(profileMapper.toProfile(profile))
                .build();
    }
}
