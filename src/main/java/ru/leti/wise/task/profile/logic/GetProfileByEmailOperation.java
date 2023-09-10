package ru.leti.wise.task.profile.logic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.leti.wise.task.profile.ProfileGrpc;
import ru.leti.wise.task.profile.error.BusinessException;
import ru.leti.wise.task.profile.error.ErrorCode;
import ru.leti.wise.task.profile.mapper.ProfileMapper;
import ru.leti.wise.task.profile.repository.ProfileRepository;

@Component
@RequiredArgsConstructor
public class GetProfileByEmailOperation {

    private final ProfileMapper profileMapper;
    private final ProfileRepository profileRepository;

    public ProfileGrpc.GetProfileByEmailResponse activate(String email) {
        var profile = profileRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.PROFILE_NOT_FOUND));

        return ProfileGrpc.GetProfileByEmailResponse.newBuilder()
                .setProfile(profileMapper.toProfile(profile))
                .build();
    }
}
