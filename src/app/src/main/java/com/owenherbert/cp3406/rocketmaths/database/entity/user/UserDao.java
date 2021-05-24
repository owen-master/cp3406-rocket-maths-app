package com.owenherbert.cp3406.rocketmaths.database.entity.user;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
* Data Access Object (DAO) for User model.
*/
@Dao
public interface UserDao {

    @Query("SELECT * FROM user ORDER BY nickname")
    List<User> getAllOrderByNickname();

    @Insert
    void insertAll(User... users);

    @Query("DELETE FROM user")
    void deleteAll();
}
