package ru.mail.techpark.lesson7_2.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface PersonDao {

    // Добавление Person в бд
    @Insert
    void insertAll(Person... people);

    // Удаление Person из бд
    @Delete
    void delete(Person person);

    // Получение всех Person из бд
    @Query("SELECT * FROM person")
    List<Person> getAllPeople();

    // Получение всех Person из бд с условием
    @Query("SELECT * FROM person WHERE favoriteColor LIKE :color")
    List<Person> getAllPeopleWithFavoriteColor(String color);

}