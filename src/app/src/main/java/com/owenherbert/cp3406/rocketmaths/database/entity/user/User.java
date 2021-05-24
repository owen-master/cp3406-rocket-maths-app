package com.owenherbert.cp3406.rocketmaths.database.entity.user;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
* User entity model.
*/
@Entity
public class User {


    @PrimaryKey @NonNull @ColumnInfo(name = "nickname")
    private String nickname;

    public User(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
