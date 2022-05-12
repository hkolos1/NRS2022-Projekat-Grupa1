package com.example.roomapp.repository

import androidx.lifecycle.LiveData
import com.example.roomapp.data.BillDao
import com.example.roomapp.data.BranchDao
import com.example.roomapp.model.Bill
import com.example.roomapp.model.Branch


class BillRepository(private val billDao: BillDao) {

    val readAllData: LiveData<List<Bill>> = billDao.readAllData()

    suspend fun addBill(bill: Bill){
        billDao.addBill(bill)
    }
}