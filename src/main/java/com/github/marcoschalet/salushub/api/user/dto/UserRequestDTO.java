package com.github.marcoschalet.salushub.api.user.dto;

import com.github.marcoschalet.salushub.domain.user.Role;

import java.util.Set;
import java.util.UUID;

public class UserRequestDTO {
    private String cpfcnpj;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UUID companyId;
    private Set<Role> roles;

    public UserRequestDTO() {}
    public UserRequestDTO(String cpfcnpj, String firstName, String lastName, String email, String password, UUID companyId, Set<Role> roles) {
        this.cpfcnpj = cpfcnpj;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.companyId = companyId;
        this.roles = roles;
    }

    public String getCpfcnpj() {
        return cpfcnpj;
    }

    public void setCpfcnpj(String cpfcnpj) {
        this.cpfcnpj = cpfcnpj;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
