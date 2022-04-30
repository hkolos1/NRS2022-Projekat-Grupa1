package com.example.roomapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "log_table")
data class Log(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val userName: String,
    val action: String,
    val time: String
): Parcelable