package com.example.roomapp.fragments.storageadmin.delivery

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Query
import com.example.roomapp.R
import com.example.roomapp.model.Product
import com.example.roomapp.viewmodel.ProductViewModel
import com.example.roomapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_delivery.view.*

class DeliveryFragment : Fragment() {

    private lateinit var mProductViewModel: ProductViewModel
    private lateinit var spinnerDelivery: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_delivery, container, false)

        val adapter = DeliveryAdapter()
        val recyclerView = view.delivery_recycler_view

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mProductViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        var listSpinner = listOf("Unassigned", "Sent", "Delivered", "Received")

        spinnerDelivery = view.findViewById(R.id.delivery_spinner)
        spinnerDelivery.adapter = ArrayAdapter<String>(inflater.context, android.R.layout.simple_spinner_dropdown_item, listSpinner);

        spinnerDelivery.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                   mProductViewModel.getProductsFromStatus(spinnerDelivery.selectedItem.toString()).observe(viewLifecycleOwner, Observer {
                       product -> adapter.setData(product)
                   })

                }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }



        return view
    }

}