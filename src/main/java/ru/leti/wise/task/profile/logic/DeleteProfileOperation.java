package ru.leti.wise.task.profile.logic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.leti.wise.task.profile.ProfileGrpc.DeleteProfileRequest;
import ru.leti.wise.task.profile.repository.ProfileRepository;
import ru.leti.wise.task.profile.validation.ProfileValidator;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteProfileOperation {

    private final ProfileRepository profileRepository;
    private final ProfileValidator profileValidator;

    public void activate(DeleteProfileRequest request) {
        profileValidator.checkForExistence(request.getProfileId());
        var uuid = UUID.fromString(request.getProfileId());
        profileRepository.deleteById(uuid);
    }
}
