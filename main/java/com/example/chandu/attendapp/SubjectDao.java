package com.example.chandu.attendapp;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface SubjectDao {

    @Insert
    void insert(Subject subject);

    @Update
    void update(Subject subject);

    @Delete
    void delete(Subject subject);

    @Query("select sum(attended)/sum(total)*100 from subject_table")
    LiveData<Float> report();

    @Query("select * from subject_table order by id asc")
    LiveData<List<Subject>> getSubjects();

}
