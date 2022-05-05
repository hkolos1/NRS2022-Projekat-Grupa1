package com.example.roomapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.roomapp.data.BranchDatabase
import com.example.roomapp.logging.LogDatabase
import com.example.roomapp.model.Branch
import com.example.roomapp.model.Log
import com.example.roomapp.repository.BranchRepository
import com.example.roomapp.repository.LogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BranchViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Branch>>
    val repository: BranchRepository

    init {
        val branchDao = BranchDatabase.getDatabase(
            application
        ).branchDao()
        repository = BranchRepository(branchDao)
        readAllData = repository.readAllData
    }

    fun addBranch(branch: Branch){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addBranch(branch)
        }
    }
}