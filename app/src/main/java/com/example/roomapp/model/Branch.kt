package com.example.roomapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "branch_table")
data class Branch(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
): Parcelable