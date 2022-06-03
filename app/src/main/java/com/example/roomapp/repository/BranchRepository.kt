package com.example.roomapp.repository

import androidx.lifecycle.LiveData
import com.example.roomapp.data.BranchDao
import com.example.roomapp.model.Branch
import com.example.roomapp.model.Category

class BranchRepository(private val branchDao: BranchDao) {

    val readAllData: LiveData<List<Branch>> = branchDao.readAllData()

    suspend fun addBranch(branch: Branch){
        branchDao.addBranch(branch)
    }

    suspend fun updateBranch(branch: Branch){
        branchDao.updateBranch(branch)
    }

    suspend fun deleteBranch(branch: Branch){
        branchDao.deleteBranch(branch)
    }

    suspend fun deleteAllBranches(){
        branchDao.deleteAllBranches()
    }
}