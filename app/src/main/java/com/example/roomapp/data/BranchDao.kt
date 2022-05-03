package com.example.roomapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.roomapp.model.Branch
import com.example.roomapp.model.Log
import com.example.roomapp.model.User


@Dao
interface BranchDao {

    @Query("SELECT * FROM branch_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Branch>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addBranch(branch: Branch)

}