package com.owenherbert.cp3406.rocketmaths.database.entity.result;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.owenherbert.cp3406.rocketmaths.database.entity.user.User;

import static androidx.room.ForeignKey.CASCADE;

/**
* Result entity model.
*/
@Entity(primaryKeys = {"userNickname", "gameDifficulty"},
        foreignKeys = {@ForeignKey(onDelete = CASCADE, entity = User.class,
                parentColumns = "nickname", childColumns = "userNickname")
})
public class Result {

    public Result(@NonNull String userNickname, @NonNull String gameDifficulty, int points, int tallyCorrect, int tallyIncorrect) {

        this.userNickname = userNickname;
        this.gameDifficulty = gameDifficulty;
        this.points = points;
        this.tallyCorrect = tallyCorrect;
        this.tallyIncorrect = tallyIncorrect;
    }

    @ColumnInfo(name = "userNickname") @NonNull
    private String userNickname;

    @ColumnInfo(name = "gameDifficulty") @NonNull
    private String gameDifficulty;

    @ColumnInfo(name = "points")
    private int points;

    @ColumnInfo(name= "tallyCorrect")
    private int tallyCorrect;

    @ColumnInfo(name = "tallyIncorrect")
    private int tallyIncorrect;

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getGameDifficulty() {
        return gameDifficulty;
    }

    public void setGameDifficulty(String gameDifficulty) {
        this.gameDifficulty = gameDifficulty;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getTallyCorrect() {
        return tallyCorrect;
    }

    public void setTallyCorrect(int tallyCorrect) {
        this.tallyCorrect = tallyCorrect;
    }

    public int getTallyIncorrect() {
        return tallyIncorrect;
    }

    public void setTallyIncorrect(int tallyIncorrect) {
        this.tallyIncorrect = tallyIncorrect;
    }
}
