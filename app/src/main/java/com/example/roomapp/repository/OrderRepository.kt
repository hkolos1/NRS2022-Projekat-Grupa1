package com.example.roomapp.repository

import androidx.lifecycle.LiveData
import com.example.roomapp.data.OrderDao
import com.example.roomapp.model.Order

class OrderRepository(private val orderDao: OrderDao) {

    val readAllData: LiveData<List<Order>> = orderDao.readAllData()

    suspend fun addOrder(order: Order){
        orderDao.addOrder(order)
    }

    suspend fun updateOrder(order: Order){
        orderDao.updateOrder(order)
    }

    suspend fun deleteOrder(order: Order){
        orderDao.deleteOrder(order)
    }

    suspend fun deleteAllOrders(){
        orderDao.deleteAllOrders()
    }

}