package com.example.roomapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.roomapp.data.CategoryDatabase
import com.example.roomapp.model.Category
import com.example.roomapp.repository.CategoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Category>>
    val repository: CategoryRepository

    init {
        val categoryDao = CategoryDatabase.getDatabase(
            application
        ).categoryDao()
        repository = CategoryRepository(categoryDao)
        readAllData = repository.readAllData
    }

    fun addCategory(category: Category){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCategory(category)
        }
    }

    fun updateCategory(category: Category){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateCategory(category)
        }
    }
}