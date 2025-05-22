package com.github.marcoschalet.salushub.infrastructure.persistence.user;

import com.github.marcoschalet.salushub.domain.user.*;

public class UserEntityMapper {

    private UserEntityMapper() {}

    public static UserEntity toEntity(User user) {
        UserEntity entity = new UserEntity();
        entity.setId(user.id().orElse(null));
        entity.setCpfcnpj(user.cpfcnpj());
        entity.setFirstName(user.firstName());
        entity.setLastName(user.lastName());
        entity.setEmail(user.email());
        entity.setPasswordHash(user.hashedPassword());
        entity.setActive(user.isActive());
        entity.setRoles(user.roles());
        entity.setCompanyId(user.companyId());
        return entity;
    }

    public static User toDomain(UserEntity entity) {
        return new User.Builder()
                .id(entity.getId())
                .cpfcnpj(new CpfCnpj(entity.getCpfcnpj()))
                .name(new Name(entity.getFirstName(), entity.getLastName()))
                .email(new Email(entity.getEmail()))
                .password(new Password(entity.getPasswordHash()))
                .companyId(entity.getCompanyId())
                .roles(entity.getRoles())
                .active(entity.isActive())
                .build();
    }
}
