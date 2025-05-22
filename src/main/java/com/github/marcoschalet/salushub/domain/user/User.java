package com.github.marcoschalet.salushub.domain.user;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class User {
    private final UUID id;
    private final CpfCnpj cpfcnpj;
    private Name name;
    private Email email;
    private Password password;
    private final Set<Role> roles;
    private final boolean active;
    private final UUID companyId;

    private User(Builder builder) {
        this.id = builder.id;
        this.cpfcnpj = builder.cpfcnpj;
        this.name = builder.name;
        this.email = builder.email;
        this.password = builder.password;
        this.roles = builder.roles;
        this.active = builder.active;
        this.companyId = builder.companyId;
    }

    public static class Builder {
        private UUID id;
        private CpfCnpj cpfcnpj;
        private Name name;
        private Email email;
        private Password password;
        private Set<Role> roles;
        private boolean active;
        private UUID companyId;

        public Builder cpfcnpj(CpfCnpj cpfcnpj) {
            this.cpfcnpj = cpfcnpj;
            return this;
        }

        public Builder name(Name name) {
            this.name = name;
            return this;
        }

        public Builder email(Email email) {
            this.email = email;
            return this;
        }

        public Builder password(Password password) {
            this.password = password;
            return this;
        }

        public Builder roles(Set<Role> roles) {
            this.roles = roles;
            return this;
        }

        public Builder active(boolean active) {
            this.active = active;
            return this;
        }

        public Builder companyId(UUID companyId) {
            this.companyId = companyId;
            return this;
        }

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public User build() {
            if (cpfcnpj == null || name == null || email == null || password == null || companyId == null || roles == null) {
                throw new IllegalArgumentException("Required fields are missing.");
            }
            return new User(this);
        }
    }

    public String cpfcnpj() {
        return cpfcnpj.value();
    }

    public boolean isActive() {
        return active;
    }

    public Set<Role> roles() {
        return roles;
    }

    public Optional<UUID> id() {
        return Optional.ofNullable(id);
    }

    public String firstName() {
        return name.firstName();
    }

    public String lastName() {
        return name.lastName();
    }

    public void updateEmail(Email email) {
        this.email = email;
    }

    public void updateName(Name name) {
        this.name = name;
    }

    public String email() {
        return email.value();
    }

    public UUID companyId() {
        return companyId;
    }

    public String hashedPassword() {
        return password.hashedPassword();
    }

    public void changePassword(Password password) {
        this.password = password;
    }
}
