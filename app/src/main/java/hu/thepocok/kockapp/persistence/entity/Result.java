package hu.thepocok.kockapp.persistence.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Result {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "cube_size")
    public int cubeSize;

    @ColumnInfo(name = "time")
    public long time;
}
