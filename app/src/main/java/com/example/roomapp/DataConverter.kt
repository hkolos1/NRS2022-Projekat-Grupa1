package com.example.roomapp

import androidx.room.TypeConverter
import com.example.roomapp.model.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataConverter {

        @TypeConverter
        fun fromStringList(value: String): MutableList<Product> {
            val listType = object : TypeToken<MutableList<Product>>() {}.type
            return Gson().fromJson(value, listType)

        }

        @TypeConverter
        fun fromStringListString(value: String): MutableList<String> {
            val listType = object : TypeToken<MutableList<String>>() {}.type
            return Gson().fromJson(value, listType)

        }

        @TypeConverter
        fun fromListLisr(list: MutableList<Product>): String {
            val gson = Gson()
            return gson.toJson(list)
        }

        @TypeConverter
        fun fromListString(list: MutableList<String>): String {
            val gson = Gson()
            return gson.toJson(list)
        }
}