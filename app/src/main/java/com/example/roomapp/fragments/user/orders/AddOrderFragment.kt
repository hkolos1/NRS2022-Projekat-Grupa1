package com.example.roomapp.fragments.user.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomapp.R
import com.example.roomapp.model.Log
import com.example.roomapp.model.Order
import com.example.roomapp.viewmodel.BranchViewModel
import com.example.roomapp.viewmodel.LogViewModel
import com.example.roomapp.viewmodel.OrderViewModel
import kotlinx.android.synthetic.main.fragment_add_order.view.*
import java.util.*


class AddOrderFragment : Fragment() {

    private  lateinit var mOrderViewModel: OrderViewModel
    private lateinit var mBranchViewModel: BranchViewModel
    private lateinit var name: TextView
    private lateinit var table : Spinner
    private lateinit var mLogViewModel: LogViewModel
    private val args by navArgs<AddOrderFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_order, container, false)

        mOrderViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)
        mLogViewModel = ViewModelProvider(this).get(LogViewModel::class.java)
        mBranchViewModel = ViewModelProvider(this).get(BranchViewModel::class.java)

        view.addOrName.text = "Order #${args.number+1}"
        name = view.addOrName
        table = view.spinnerTable

        val spinnerProdAdapter = ArrayAdapter<Any>(requireContext(), android.R.layout.simple_spinner_dropdown_item)

        view.addOrder.setOnClickListener {
            insertDataToDatabase()
        }

        return view
    }

    private fun insertDataToDatabase() {
        val tab = table.selectedItem

        val order = Order(0,name.text.toString(),args.user.branch!!,tab.toString(),
            0, mutableListOf(),0,false,0,null)
        mOrderViewModel.addOrder(order)
        val cal: Calendar = Calendar.getInstance()
        mLogViewModel.addLog(Log(0,args.user.firstName,"Added order ${order.name}",cal.time.toString()))

        Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
        findNavController().navigateUp()
    }


}