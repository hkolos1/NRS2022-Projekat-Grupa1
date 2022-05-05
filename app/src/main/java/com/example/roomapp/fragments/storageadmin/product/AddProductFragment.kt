package com.example.roomapp.fragments.storageadmin.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.roomapp.R
import com.example.roomapp.model.Product
import com.example.roomapp.viewmodel.ProductViewModel
import com.example.roomapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import kotlinx.android.synthetic.main.fragment_add_product.*
import kotlinx.android.synthetic.main.fragment_add_product.view.*


class AddProductFragment : Fragment() {

    private  lateinit var mProductViewModel: ProductViewModel
    private lateinit var name:TextView
    private lateinit var quantity:TextView
    private lateinit var deStatus:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_product, container, false)

        mProductViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        name = view.findViewById(R.id.addPrName)
        quantity = view.findViewById(R.id.addPrAmount)
        deStatus = view.findViewById<Spinner>(R.id.addPrCode).selectedItem.toString()

        view.addButton.setOnClickListener {
            insertDataToDatabase()
        }


        return view
    }

    private fun insertDataToDatabase() {
        val value= quantity.text.toString();
        val finalValue=Integer.parseInt(value)
        val product = Product(
            0,name.text.toString(),finalValue,null,deStatus)
        mProductViewModel.addProduct(product)
        Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_addProduct_to_fragmentProduct)
    }


}