package com.example.roomapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.roomapp.model.Branch
import com.example.roomapp.model.Log
import com.example.roomapp.model.Product
import com.example.roomapp.model.User


@Dao
interface BranchDao {

    @Query("SELECT * FROM branch_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Branch>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBranch(branch: Branch)

}