package com.example.roomapp.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "order_table")
data class Order(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val branch: String,
    val table: String,
    var productsQuantity: Int,
    val products: MutableList<Product>,
    var total: Double,
    var bill: Boolean,
    var billDate: String?
): Parcelable