package com.example.roomapp.data
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.roomapp.model.Bill

@Dao
interface BillDao {

    @Query("SELECT * FROM bill_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Bill>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addBill(bill: Bill)

}