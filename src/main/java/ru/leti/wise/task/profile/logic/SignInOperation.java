package ru.leti.wise.task.profile.logic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.leti.wise.task.profile.ProfileGrpc.SignInResponse;
import ru.leti.wise.task.profile.ProfileGrpc.SignInRequest;
import ru.leti.wise.task.profile.error.ProfileNotFoundException;
import ru.leti.wise.task.profile.model.ProfileEntity;
import ru.leti.wise.task.profile.repository.ProfileRepository;
import ru.leti.wise.task.profile.service.JWTHelper;

//TODO: errors
@Component
@RequiredArgsConstructor
public class SignInOperation {

    private final JWTHelper jwtHelper;
    private final ProfileRepository profileRepository;

    public SignInResponse activate(SignInRequest request) {

        ProfileEntity profile = profileRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ProfileNotFoundException());

        if (!profile.getProfilePassword().equals(request.getPassword())) {
            throw new RuntimeException("wrong password");
        }

        return SignInResponse.newBuilder()
                .setToken(jwtHelper.generateToken(profile))
                .build();
    }
}
