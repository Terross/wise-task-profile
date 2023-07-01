package ru.leti.wise.task.profile.logic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.leti.wise.task.profile.ProfileGrpc.GetProfileResponse;
import ru.leti.wise.task.profile.mapper.ProfileMapper;
import ru.leti.wise.task.profile.repository.ProfileRepository;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetProfileOperation {

    private final ProfileMapper profileMapper;
    private final ProfileRepository profileRepository;

    public GetProfileResponse activate(UUID id) {
        var profile = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile with id = %s not found".formatted(id)));

        return GetProfileResponse.newBuilder()
                .setProfile(profileMapper.toProfile(profile))
                .build();
    }
}
