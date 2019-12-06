package com.example.a275del3.dummy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import android.os.Bundle;
import android.provider.SyncStateContract;

import java.util.List;

@Dao
public interface MatchupChartDao {

//    @Query("SELECT * FROM user "+ Constants.TABLE_NAME_NOTE)
//    List<Chart> getAll();
    @Query("SELECT * FROM MU_db")
    List<MatchupChart.Chart> getCharts();

    /*
     * Insert the object in database
     * @param note, object to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(MatchupChart.Chart chart);

    /*
     * update the object in database
     * @param note, object to be updated
     */
    @Update
    void update(MatchupChart.Chart repos);

    /*
     * delete the object from database
     * @param note, object to be deleted
     */
    @Delete
    void delete(MatchupChart.Chart chart);

    /*
     * delete list of objects from database
     * @param note, array of objects to be deleted
     */
    @Delete
    void delete(MatchupChart.Chart... chart);      // Note... is varargs, here note is an array

}
