package com.example.roomapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.roomapp.DataConverter
import com.example.roomapp.model.Branch

@Database(entities = [Branch::class], version = 1, exportSchema = false)
@TypeConverters(DataConverter::class)
abstract class BranchDatabase : RoomDatabase() {

    abstract fun branchDao(): BranchDao

    companion object {
        @Volatile
        private var INSTANCE: BranchDatabase? = null

        fun getDatabase(context: Context): BranchDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BranchDatabase::class.java,
                    "branch_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}