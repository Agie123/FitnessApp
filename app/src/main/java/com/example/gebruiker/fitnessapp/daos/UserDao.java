package com.example.gebruiker.fitnessapp.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.example.gebruiker.fitnessapp.objects.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM User where email= :mail and password= :password")
    User getUser(String mail, String password);

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM User")
    public List<User> getAllUsers();
}