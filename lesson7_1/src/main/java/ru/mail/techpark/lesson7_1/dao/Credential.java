package ru.mail.techpark.lesson7_1.dao;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Credential")
public class Credential {
    @PrimaryKey
    @NonNull
    public String name;

    public String pass;

    public Credential() {
    }

    public Credential(String key, String value) {
        name = key;
        pass = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credential that = (Credential) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(pass, that.pass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, pass);
    }
}
