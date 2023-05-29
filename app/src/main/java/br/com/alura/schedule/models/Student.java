package br.com.alura.schedule.models;

import androidx.annotation.NonNull;

public class Student {
    private final String name;
    private final String telephone;
    private final String email;

    public Student(String name, String telephone, String email) {
        this.name = name;
        this.telephone = telephone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getEmail() {
        return email;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
