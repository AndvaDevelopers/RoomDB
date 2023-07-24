package com.example.roomdb;



import static androidx.room.OnConflictStrategy.REPLACE;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MainDao {

    @Insert(onConflict = REPLACE)
    public void insert(MyData myData);

    @Delete
    public void delete(MyData myData);

    @Delete
    void reset(List<MyData> myData);

    @Query("update Details set text = :sText Where id = :sId")
    void update(int sId,String sText);

    @Query("select * from Details")
    List<MyData> getAll();

}
