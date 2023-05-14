package ru.leti.wise.task.profile.logic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.leti.wise.task.profile.ProfileGrpc.GetAllProfilesResponse;
import ru.leti.wise.task.profile.mapper.ProfileMapper;
import ru.leti.wise.task.profile.repository.ProfileRepository;

@Component
@RequiredArgsConstructor
public class GetAllProfilesOperation {

    private final ProfileMapper profileMapper;
    private final ProfileRepository profileRepository;

    public GetAllProfilesResponse activate() {
        return GetAllProfilesResponse.newBuilder()
                .addAllProfile(profileMapper.toProfiles(profileRepository.findAll()))
                .build();
    }
}
