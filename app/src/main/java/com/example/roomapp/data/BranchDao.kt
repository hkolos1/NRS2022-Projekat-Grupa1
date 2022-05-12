package com.example.roomapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.roomapp.model.*


@Dao
interface BranchDao {

    @Query("SELECT * FROM branch_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Branch>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBranch(branch: Branch)

    @Update
    suspend fun updateBranch(branch: Branch)

    @Delete
    suspend fun deleteBranch(branch: Branch)

    @Query("DELETE FROM branch_table")
    suspend fun deleteAllBranches()
}