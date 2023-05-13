package ru.leti.wise.task.profile.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import ru.leti.wise.task.profile.ProfileOuterClass;
import ru.leti.wise.task.profile.ProfileOuterClass.Profile;
import ru.leti.wise.task.profile.model.ProfileEntity;
import ru.leti.wise.task.profile.model.Role;

import java.util.List;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ProfileMapper {


    Profile toProfile(ProfileEntity profile);
    ProfileEntity toProfileEntity(Profile profile);

    List<Profile> toProfiles(List<ProfileEntity> profiles);

    default ProfileOuterClass.Role toRole(Role role) {
        return ProfileOuterClass.Role.valueOf(role.name());
    }

    default Role toRole(ProfileOuterClass.Role role) {
        return Role.valueOf(role.name());
    }
}
