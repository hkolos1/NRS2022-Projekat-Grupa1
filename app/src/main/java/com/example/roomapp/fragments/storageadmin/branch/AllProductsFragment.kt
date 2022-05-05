package com.example.roomapp.fragments.storageadmin.branch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomapp.R
import com.example.roomapp.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_all_products.view.*

class AllProductsFragment: Fragment() {
    private lateinit var mProductViewModel: ProductViewModel
    private val args by navArgs<BranchChooserFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all_products, container, false)

        val adapter = AllProductsAdapter()
        val recyclerView = view.recyclerProductsInBranch

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        mProductViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)


        mProductViewModel.getProductsFromBranch(args.currentBranch.id).observe(viewLifecycleOwner, Observer{
                product -> adapter.setData(product)
        })



        mProductViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)


        return view
    }
}