package com.example.roomapp.fragments.storageadmin.branch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.roomapp.R
import com.example.roomapp.model.Branch
import com.example.roomapp.model.Product
import com.example.roomapp.viewmodel.BranchViewModel
import com.example.roomapp.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_add_products.view.*

class AddProductsFragment: Fragment() {

    private lateinit var mProductViewModel: ProductViewModel
    private lateinit var mBranchViewModel: BranchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_products, container, false)

        mProductViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        mBranchViewModel = ViewModelProvider(this).get(BranchViewModel::class.java)

        val spinnerProducts = view.spinner_assign_product_product
        val spinnerBranches = view.spinner_assign_product_branch


        val spinnerProdAdapter = ArrayAdapter<Any>(requireContext(), android.R.layout.simple_spinner_dropdown_item)
        val spinnerBranchesAdapter = ArrayAdapter<Any>(requireContext(), android.R.layout.simple_spinner_dropdown_item)


        mProductViewModel.readAllData.observe(viewLifecycleOwner, Observer{
            product -> product.forEach {
                spinnerProdAdapter.add(it)
        }
        })

        mBranchViewModel.readAllData.observe(viewLifecycleOwner, Observer{
            product -> product.forEach{
                spinnerBranchesAdapter.add(it)
        }
        })

        spinnerProducts.adapter = spinnerProdAdapter
        spinnerBranches.adapter = spinnerBranchesAdapter


        view.btn_assign_product.setOnClickListener{
            val chosenProduct: Product = spinnerProducts.selectedItem as Product
            val chosenBranch: Branch = spinnerBranches.selectedItem as Branch

            val newProduct = Product(chosenProduct.id, chosenProduct.prodName, chosenProduct.quantity, chosenBranch.id, chosenProduct.deliveryStatus)

            if(chosenProduct.branchId == chosenBranch.id){
                Toast.makeText(requireContext(), chosenProduct.prodName + " is already assigned to " + chosenBranch.name, Toast.LENGTH_SHORT).show()
            }else{
                mProductViewModel.updateProduct(newProduct)
                Toast.makeText(requireContext(), "Assigned " + chosenProduct.prodName + " to " + chosenBranch.name + " successfully!", Toast.LENGTH_SHORT).show()

                val action = AddProductsFragmentDirections.actionAddProductsToBranches()
                findNavController().navigate(action)
            }
        }


        return view
    }
}