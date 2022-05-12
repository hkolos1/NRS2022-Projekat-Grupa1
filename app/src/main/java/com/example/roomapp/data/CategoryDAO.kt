package com.example.roomapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.roomapp.model.Category
import com.example.roomapp.model.Product

@Dao
interface CategoryDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCategory(category: Category)

    @Update
    suspend fun updateCategory(category: Category)

    @Query("SELECT * FROM category_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Category>>

    @Delete
    suspend fun deleteCategory(category: Category)

    @Query("DELETE FROM category_table")
    suspend fun deleteAllCategories()

}