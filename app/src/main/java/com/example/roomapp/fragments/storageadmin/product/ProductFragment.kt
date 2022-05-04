package com.example.roomapp.fragments.storageadmin.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomapp.R
import com.example.roomapp.fragments.admin.AdminFragmentArgs
import com.example.roomapp.viewmodel.ProductViewModel
import com.example.roomapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.android.synthetic.main.fragment_product.view.*

class ProductFragment : Fragment() {

    private lateinit var addButton : Button
    private lateinit var mProductViewModel: ProductViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_product, container, false)
        val adapter = ProductAdapter()
        val recyclerView = view.recyclerViewProd
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mProductViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        mProductViewModel.readAllData.observe(viewLifecycleOwner, Observer {product ->
            adapter.setData(product)
        })
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