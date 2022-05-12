package com.example.roomapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.roomapp.data.BillDatabase
import com.example.roomapp.data.BranchDatabase
import com.example.roomapp.model.Bill
import com.example.roomapp.model.Branch
import com.example.roomapp.repository.BillRepository
import com.example.roomapp.repository.BranchRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BillViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Bill>>
    val repository: BillRepository

    init {
        val billDao = BillDatabase.getDatabase(
            application
        ).billDao()
        repository = BillRepository(billDao)
        readAllData = repository.readAllData
    }

    fun addBill(bill: Bill){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addBill(bill)
        }
    }
}