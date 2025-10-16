package ru.leti.wise.task.profile.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.leti.wise.task.profile.ProfileOuterClass.Profile;
import ru.leti.wise.task.profile.error.BusinessException;
import ru.leti.wise.task.profile.error.ErrorCode;
import ru.leti.wise.task.profile.model.ProfileEntity;
import ru.leti.wise.task.profile.repository.ProfileRepository;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProfileValidator {

    private final ProfileRepository profileRepository;
    public void checkEmptyFields(Profile profile){
        if(profile.getEmail().isBlank() || profile.getProfilePassword().isBlank()
                || profile.getFirstName().isBlank() || profile.getLastName().isBlank() || profile.getStudentGroup().isBlank()){
            throw new BusinessException(ErrorCode.EMPTY_FIELDS);
        }
    }
    public void checkEmptyFieldsForUpdate(Profile profile){
        if(profile.getFirstName().isBlank() || profile.getEmail().isBlank() ||profile.getLastName().isBlank() || profile.getStudentGroup().isBlank()){
            throw new BusinessException(ErrorCode.EMPTY_FIELDS);
        }
    }

    public void checkUniqueEmail(Profile profile){
        String email = profile.getEmail();
        profileRepository.findByEmail(email).ifPresent(entity -> {
            throw new BusinessException(ErrorCode.EMAIL_ALREADY_TAKEN);
        });
    }

    public void checkEmail(String email){ // здесь можно проверять корректность email
        if(email.isBlank()){
            throw new BusinessException(ErrorCode.EMPTY_FIELDS);
        }
    }

    public ProfileEntity checkForExistence(String uuid){
        checkUuid(uuid);
        var id = UUID.fromString(uuid);
        return profileRepository.findById(id).orElseThrow(() ->
                new BusinessException(ErrorCode.PROFILE_NOT_FOUND)
        );
    }

    public void checkUuid(String uuid){ // здесь можно проверять корректность uuid
        if(uuid.isBlank()){
            throw new BusinessException(ErrorCode.EMPTY_FIELDS);
        }
    }

    public ProfileEntity checkEmailExistence(String email){
        checkEmail(email);
        return profileRepository.findByEmail(email).orElseThrow(() -> new BusinessException(ErrorCode.PROFILE_NOT_FOUND));
    }

}
