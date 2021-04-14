package ru.mail.techpark.lesson7_1.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface CredentialDao {

    @Query("SELECT * FROM Credential")
    List<Credential> getAll();

    @Query("SELECT * FROM Credential WHERE name=:name")
    Credential get(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCredential(Credential credential);
}
