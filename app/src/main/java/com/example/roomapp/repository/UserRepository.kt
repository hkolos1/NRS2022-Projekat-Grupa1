package com.example.roomapp.repository

import android.util.Log.*
import androidx.lifecycle.LiveData
import com.example.roomapp.data.UserDao
import com.example.roomapp.model.User

class UserRepository(private val userDao: UserDao) {

    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User){
        userDao.addUser(user)
    }

    suspend fun updateUser(user: User){
        userDao.updateUser(user)
    }

    suspend fun deleteUser(user: User){
        userDao.deleteUser(user)
    }

    suspend fun deleteAllUsers(){
        userDao.deleteAllUsers()
    }

    suspend fun getUserName(userName: String): User?{
        i("MYTAG", "inside Repository Getusers fun ")
        return userDao.getUsername(userName)
    }

}