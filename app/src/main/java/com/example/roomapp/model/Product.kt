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
    var quantity: Double,
    val unit: String,
    val branchId: Int?,
    var deliveryStatus: String,
    val category: String?,
    val price: Double,
    val round:Boolean
): Parcelable{
    override fun toString(): String {
        return prodName
    }
}