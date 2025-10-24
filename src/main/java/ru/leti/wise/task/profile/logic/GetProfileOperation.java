package ru.leti.wise.task.profile.logic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.leti.wise.task.profile.ProfileGrpc.GetProfileRequest;
import ru.leti.wise.task.profile.ProfileGrpc.GetProfileResponse;
import ru.leti.wise.task.profile.error.BusinessException;
import ru.leti.wise.task.profile.error.ErrorCode;
import ru.leti.wise.task.profile.mapper.ProfileMapper;
import ru.leti.wise.task.profile.repository.ProfileRepository;
import ru.leti.wise.task.profile.validation.ProfileValidator;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetProfileOperation {

    private final ProfileMapper profileMapper;
    private final ProfileValidator profileValidator;

    public GetProfileResponse activate(GetProfileRequest request) {
        var profile = profileValidator.checkForExistence(request.getProfileId());

        return GetProfileResponse.newBuilder()
                .setProfile(profileMapper.toProfile(profile))
                .build();
    }
}
