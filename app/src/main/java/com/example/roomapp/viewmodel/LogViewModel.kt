package com.example.roomapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.roomapp.logging.LogDatabase
import com.example.roomapp.model.Log
import com.example.roomapp.repository.LogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LogViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Log>>
    val repository: LogRepository

    init {
        val logDao = LogDatabase.getDatabase(
            application
        ).logDao()
        repository = LogRepository(logDao)
        readAllData = repository.readAllData
    }

    fun addLog(log: Log){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addLog(log)
        }
    }

}