package com.example.roomapp.fragments.user.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.roomapp.R
import com.example.roomapp.viewmodel.LogViewModel
import com.example.roomapp.viewmodel.OrderViewModel

class AddProductToOrderFragment : Fragment() {

    private lateinit var mOrderViewModel: OrderViewModel
    private lateinit var mLogViewModel: LogViewModel
    private val args by navArgs<AddProductToOrderFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_order_add_product, container, false)

        return view
    }

}