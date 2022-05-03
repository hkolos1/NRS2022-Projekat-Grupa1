package com.example.roomapp.repository

import androidx.lifecycle.LiveData
import com.example.roomapp.data.BranchDao
import com.example.roomapp.model.Branch
import com.example.roomapp.model.Log

class BranchRepository(private val branchDao: BranchDao) {

    val readAllData: LiveData<List<Branch>> = branchDao.readAllData()

    suspend fun addBranch(branch: Branch){
        branchDao.addBranch(branch)
    }

}