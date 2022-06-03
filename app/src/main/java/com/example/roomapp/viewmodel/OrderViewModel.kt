package com.example.roomapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.roomapp.data.OrderDatabase
import com.example.roomapp.model.Order
import com.example.roomapp.model.Product
import com.example.roomapp.model.User
import com.example.roomapp.repository.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<Order>>
    val repository: OrderRepository

    init {
        val orderDao = OrderDatabase.getDatabase(
            application
        ).orderDao()
        repository = OrderRepository(orderDao)
        readAllData = repository.readAllData
    }

    fun addOrder(order: Order){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addOrder(order)
        }
    }

    fun updateOrder(order: Order){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateOrder(order)
        }
    }

    fun deleteOrder(order: Order){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteOrder(order)
        }
    }

    fun deleteAllOrders(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllOrders()
        }
    }
}