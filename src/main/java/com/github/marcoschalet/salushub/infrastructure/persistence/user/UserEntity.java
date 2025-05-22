package com.github.marcoschalet.salushub.infrastructure.persistence.user;

import com.github.marcoschalet.salushub.domain.user.*;

import jakarta.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    private UUID id;

    private String cpfcnpj;

    private String firstName;

    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

    private String passwordHash;

    private boolean active;

    private UUID companyId;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<Role> roles;

    public static UserEntity fromDomain(User user) {
        UserEntity entity = new UserEntity();
        entity.id = user.id().orElse(UUID.randomUUID());
        entity.cpfcnpj = user.cpfcnpj();
        entity.firstName = user.firstName();
        entity.lastName = user.lastName();
        entity.email = user.email();
        entity.passwordHash = user.hashedPassword();
        entity.active = user.isActive();
        entity.roles = user.roles();
        entity.companyId = user.companyId();
        return entity;
    }

    public User toDomain() {
        return new User.Builder()
                .id(id)
                .name(new Name(firstName, lastName))
                .cpfcnpj(new CpfCnpj(cpfcnpj))
                .email(new Email(email))
                .password(new Password(passwordHash))
                .companyId(getCompanyId())
                .roles(roles)
                .active(active)
                .build();
    }

    public String getCpfcnpj() {
        return cpfcnpj;
    }

    public void setCpfcnpj(String cpfcnpj) {
        this.cpfcnpj = cpfcnpj;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public UUID getCompanyId() {
        return companyId;
    }

    public void setCompanyId(UUID companyId) {
        this.companyId = companyId;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
