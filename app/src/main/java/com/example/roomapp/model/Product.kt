package com.example.roomapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "product_table")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val prodName: String,
    val quantity: Int,
    val branchId: Int,
    val deliveryStatus: String
): Parcelable{
    override fun toString(): String {
        return prodName
    }
}
