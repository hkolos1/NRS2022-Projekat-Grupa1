package com.example.roomapp.fragments.storageadmin.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomapp.R
import com.example.roomapp.fragments.admin.AdminFragmentArgs
import com.example.roomapp.viewmodel.UserViewModel

class ProductFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var addButton : Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_product, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        addButton = view.findViewById(R.id.addProdButton)
        addButton.setOnClickListener {
            addingProduct()
        }

        return view
    }

    private fun addingProduct() {
        findNavController().navigate(R.id.action_productFragment_to_addProduct)
    }

}