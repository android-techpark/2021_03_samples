package ru.mail.techpark.lesson7_2.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Person {
    @PrimaryKey
    @NonNull
    String name;
    int age;
    String favoriteColor;
}
