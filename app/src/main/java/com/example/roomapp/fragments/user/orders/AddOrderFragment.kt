package com.example.roomapp.fragments.user.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomapp.R
import com.example.roomapp.model.Log
import com.example.roomapp.model.Order
import com.example.roomapp.model.Product
import com.example.roomapp.viewmodel.LogViewModel
import com.example.roomapp.viewmodel.OrderViewModel
import com.example.roomapp.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_add_product.view.*
import kotlinx.android.synthetic.main.fragment_order_add.view.*
import java.util.*


class AddOrderFragment : Fragment() {

    private lateinit var mProductViewModel: ProductViewModel
    private lateinit var mOrderViewModel: OrderViewModel
    private lateinit var mLogViewModel: LogViewModel
    private lateinit var order: Order
    private val args by navArgs<AddOrderFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_order_add, container, false)

        mProductViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        mLogViewModel = ViewModelProvider(this).get(LogViewModel::class.java)
        mOrderViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)

        val adapter = AddOrderAdapter()
        val recyclerView = view.listOrder
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val cal = Calendar.getInstance()
        order = Order(0,args.user.id,cal.time.toString(), mutableListOf(
            Product(0,"Test",11,"Kesa",0,"status")),0)

        order.products?.let { adapter.setData(it) }

        view.btn_add_order2.setOnClickListener {
            val action = AddOrderFragmentDirections.actionAddOrderFragmentToAddProductToOrderFragment(args.user,order)
            findNavController().navigate(action)
        }

        view.btnFinishOrder.setOnClickListener {
            insertDataToDatabase()
        }

        return view
    }

    private fun insertDataToDatabase() {
        mOrderViewModel.addOrder(order)
        val cal: Calendar = Calendar.getInstance()
        mLogViewModel.addLog(Log(0,args.user.firstName,"Added order",cal.time.toString()))

        Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
        val action = AddOrderFragmentDirections.actionAddOrderFragmentToBillFragment(order)
        findNavController().navigate(action)
    }


}