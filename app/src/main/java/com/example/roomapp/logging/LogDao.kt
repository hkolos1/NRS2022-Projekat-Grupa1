package com.example.roomapp.logging

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.roomapp.model.Log

@Dao
interface LogDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addLog(log: Log)

    @Query("SELECT * FROM log_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Log>>

}