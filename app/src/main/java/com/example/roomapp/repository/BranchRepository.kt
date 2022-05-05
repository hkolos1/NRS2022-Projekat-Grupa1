package com.example.roomapp.repository

import androidx.lifecycle.LiveData
import com.example.roomapp.data.BranchDao
import com.example.roomapp.model.Branch

class BranchRepository(private val branchDao: BranchDao) {

    val readAllData: LiveData<List<Branch>> = branchDao.readAllData()


}