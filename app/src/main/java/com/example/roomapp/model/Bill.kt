package com.example.roomapp.model
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
@Parcelize
@Entity(tableName = "bill_table")
data class Bill(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val orderId: Int,
    val amount : Double
): Parcelable