package com.example.projectandroid.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.projectandroid.model.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert
    void insertUsers(User... users);

    @Update
    void updateUsers(User... users);

    @Delete
    void deleteUsers(User... users);

    @Query("SELECT * FROM user")
    List<User> getAllUser();

    @Query("SELECT * FROM user WHERE idUser like :userName  and password like :password")
    List<User> checkUserLogin(String userName, String password);

    @Query("SELECT * FROM USER WHERE idUser like :userName")
    List<User> getUserByUserName(String userName);
}
