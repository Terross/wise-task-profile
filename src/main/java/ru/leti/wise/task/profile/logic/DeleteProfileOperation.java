package ru.leti.wise.task.profile.logic;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.leti.wise.task.profile.repository.ProfileRepository;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteProfileOperation {

    private final ProfileRepository profileRepository;

    public void activate(UUID id) {
        profileRepository.deleteById(id);
    }
}
