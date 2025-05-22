package com.github.marcoschalet.salushub.domain.user;

public record Name(String firstName, String lastName) {
    public Name {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("First name cannot be null or blank");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("Last name cannot be null or blank");
        }
    }

    public String fullName() {
        return firstName + " " + lastName;
    }

    public Name withFirstName(String newFirstName) {
        return new Name(newFirstName, this.lastName);
    }

    public Name withLastName(String newLastName) {
        return new Name(this.firstName, newLastName);
    }
}
