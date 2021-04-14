package ru.mail.techpark.lesson7_2.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
interface SimpleEntityDao {

    @Insert
    void insertAll(SimpleEntity... entities);

    // Получение всех SimpleEntity из бд
    @Query("SELECT * FROM simple_entity")
    List<SimpleEntity> getAllEntities();

    @Delete
    void delete(SimpleEntity... entities);
}
