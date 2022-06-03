package com.example.roomapp.repository

import androidx.lifecycle.LiveData
import com.example.roomapp.logging.LogDao
import com.example.roomapp.model.Log

class LogRepository(private val logDao: LogDao) {

    val readAllData: LiveData<List<Log>> = logDao.readAllData()

    suspend fun addLog(log: Log){
        logDao.addLog(log)
    }

}