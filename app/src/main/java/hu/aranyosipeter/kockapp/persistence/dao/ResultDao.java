package hu.aranyosipeter.kockapp.persistence.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import hu.aranyosipeter.kockapp.persistence.entity.Result;

@Dao
public interface ResultDao {
    @Query("SELECT * FROM result WHERE cube_size = (:cubeSize) ORDER BY time ASC LIMIT 1")
    List<Result> getBestOfSize(int cubeSize);

    @Insert
    void insert(Result result);

    @Query("DELETE FROM result")
    void deleteAll();
}
