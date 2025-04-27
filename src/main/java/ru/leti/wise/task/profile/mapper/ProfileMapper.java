package ru.leti.wise.task.profile.mapper;

import org.mapstruct.*;
import ru.leti.wise.task.profile.ProfileOuterClass;
import ru.leti.wise.task.profile.ProfileOuterClass.Profile;
import ru.leti.wise.task.profile.model.ProfileEntity;
import ru.leti.wise.task.profile.model.Role;

import java.util.List;

@Mapper(componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ProfileMapper {

    @Mapping(target = "profilePassword", ignore = true)
    Profile toProfile(ProfileEntity profile);

    @Mapping(target = "id",
            conditionExpression = "java(profile.getId() != null && !profile.getId().isEmpty())", defaultExpression = "java(null)")
    ProfileEntity toProfileEntity(Profile profile);

    List<Profile> toProfiles(List<ProfileEntity> profiles);

    default ProfileOuterClass.Role toRole(Role role) {
        return ProfileOuterClass.Role.valueOf(role.name());
    }

    default Role toRole(ProfileOuterClass.Role role) {
        return Role.valueOf(role.name());
    }
}
