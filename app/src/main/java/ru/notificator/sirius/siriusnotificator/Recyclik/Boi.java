package ru.notificator.sirius.siriusnotificator.Recyclik;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class Boi implements Serializable {
    private String name;
    private String surname;
    private String Mac;

    public Boi(String name, String surname, String Mac) {
        this.name = name;
        this.surname = surname;
        this.Mac = Mac;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getMac() {
        return Mac;
    }

    public void setMac(String Mac) {
        this.Mac = Mac;
    }
}
