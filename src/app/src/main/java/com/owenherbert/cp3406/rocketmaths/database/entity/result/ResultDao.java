package com.owenherbert.cp3406.rocketmaths.database.entity.result;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
* Data Access Object (DAO) for Result model.
*/
@Dao
public interface ResultDao {

    @Query("SELECT * FROM result WHERE gameDifficulty = :gameDifficulty ORDER BY points DESC")
    List<Result> getResultsByGameDifficulty(String gameDifficulty);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Result result);

    @Query("SELECT * FROM result WHERE userNickname = :userNickname AND gameDifficulty = :gameDifficulty")
    Result getResults(String userNickname, String gameDifficulty);

    @Query("DELETE FROM result")
    void deleteAll();
}
