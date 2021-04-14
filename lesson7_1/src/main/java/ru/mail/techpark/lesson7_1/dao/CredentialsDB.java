package ru.mail.techpark.lesson7_1.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Credential.class}, version = 1)
public abstract class CredentialsDB extends RoomDatabase {
    public abstract CredentialDao getCredentialDao();
}
