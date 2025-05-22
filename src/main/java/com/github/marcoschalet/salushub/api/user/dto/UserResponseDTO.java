package com.github.marcoschalet.salushub.api.user.dto;

import com.github.marcoschalet.salushub.domain.user.Role;

import java.util.Set;
import java.util.UUID;

public class UserResponseDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private UUID companyId;
    private Set<Role> roles;

    public UserResponseDTO() {}
    public UserResponseDTO(UUID id, String firstName, String lastName, String email, UUID companyId, Set<Role> roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.companyId = companyId;
        this.roles = roles;
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
