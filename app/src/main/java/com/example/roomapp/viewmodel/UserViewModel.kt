package com.example.roomapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.roomapp.data.UserDatabase
import com.example.roomapp.logging.LogDatabase
import com.example.roomapp.model.Log
import com.example.roomapp.repository.UserRepository
import com.example.roomapp.model.User
import com.example.roomapp.repository.LogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class UserViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<User>>
    val repository: UserRepository
    val logRep: LogRepository
    var currentUser: User? = null
    val cal: Calendar = Calendar.getInstance()

    init {
        val userDao = UserDatabase.getDatabase(
            application
        ).userDao()
        repository = UserRepository(userDao)
        val logDao = LogDatabase.getDatabase(
            application
        ).logDao()
        logRep = LogRepository(logDao)
        readAllData = repository.readAllData
    }

    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUser(user)
            logRep.addLog(Log(0,user.firstName,"Added",cal.time.toString()))
        }
    }

    fun updateUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUser(user)
            logRep.addLog(Log(0,user.firstName,"Updated",cal.time.toString()))
        }
    }

    fun deleteUser(user: User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(user)
            logRep.addLog(Log(0,user.firstName,"Removed",cal.time.toString()))
        }
    }

    fun deleteAllUsers(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllUsers()
            logRep.addLog(Log(0,"All users","Removed",cal.time.toString()))
        }
    }

    fun getUser(username:String){
        viewModelScope.launch(Dispatchers.IO) {
            currentUser=repository.getUserName(username)
        }
    }

}