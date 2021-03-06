package com.example.roomapp.fragments.storageadmin.branch.products

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
import androidx.navigation.fragment.navArgs
import com.example.roomapp.R
import com.example.roomapp.model.Branch
import com.example.roomapp.model.Log
import com.example.roomapp.model.Product
import com.example.roomapp.viewmodel.BranchViewModel
import com.example.roomapp.viewmodel.LogViewModel
import com.example.roomapp.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_add_products.*
import kotlinx.android.synthetic.main.fragment_add_products.view.*
import java.math.RoundingMode
import java.util.*

class AddProductsFragment: Fragment() {

    private lateinit var mProductViewModel: ProductViewModel
    private lateinit var mBranchViewModel: BranchViewModel
    private lateinit var mLogViewModel: LogViewModel
    private val args by navArgs<AddProductsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_products, container, false)

        mProductViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        mBranchViewModel = ViewModelProvider(this).get(BranchViewModel::class.java)
        mLogViewModel = ViewModelProvider(this).get(LogViewModel::class.java)

        val spinnerProducts = view.spinner_assign_product_product
        view.textViewBranch.text = "Branch "+args.branch.name

        val spinnerProdAdapter = ArrayAdapter<Any>(requireContext(), android.R.layout.simple_spinner_dropdown_item)

        mProductViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            products -> products.forEach {
                var da = true
                args.branch.products.forEach {
                    prod ->
                    if(it.prodName == prod.prodName){
                        da = false
                    }
                }
                if(da) spinnerProdAdapter.add(it)
            }
        })

        spinnerProducts.adapter = spinnerProdAdapter


        view.btn_assign_product.setOnClickListener{
            val chosenProduct: Product = spinnerProducts.selectedItem as Product
            val chosenBranch: Branch = args.branch
            var quantity = edit_assign_product_quantity.text.toString().toDouble()
            quantity=quantity.toBigDecimal().setScale(3, RoundingMode.UP).toDouble()

            if(quantity>chosenProduct.quantity){
                Toast.makeText(requireContext(), "Not valid quantity for "+chosenProduct.prodName, Toast.LENGTH_SHORT).show()
            }else if(args.branch.products.contains(chosenProduct)){
                Toast.makeText(requireContext(), chosenProduct.prodName + " is already assigned to " + chosenBranch.name, Toast.LENGTH_SHORT).show()
            }else{
                var quant=(chosenProduct.quantity-quantity)
                if (chosenProduct.round.equals("Round")) {
                 quant=quant.toInt().toDouble()
                }
                val newProduct = Product(chosenProduct.id, chosenProduct.prodName,
                   quant.toBigDecimal().setScale(3, RoundingMode.UP).toDouble(),// <-- Oduzima dio kolicine iz skladista
                    chosenProduct.unit,null, chosenProduct.deliveryStatus,
                    chosenProduct.category, chosenProduct.price,chosenProduct.round)

                if (chosenProduct.round==false) {
                    quantity=quantity.toInt().toDouble()
                }

                args.branch.products.add(Product(chosenProduct.id,chosenProduct.prodName,quantity,
                    chosenProduct.unit,chosenBranch.id,chosenProduct.deliveryStatus,
                    chosenProduct.category,chosenProduct.price,chosenProduct.round))
                mBranchViewModel.updateBranch(args.branch)

                mProductViewModel.updateProduct(newProduct)

                val cal: Calendar = Calendar.getInstance()
                mLogViewModel.addLog(Log(0,args.user.firstName,"Assigned ${chosenProduct.prodName} to ${chosenBranch.name}",cal.time.toString()))
                Toast.makeText(requireContext(), "Assigned " + chosenProduct.prodName + " to " + chosenBranch.name + " successfully!", Toast.LENGTH_SHORT).show()

                findNavController().navigateUp()
            }
        }

        return view
    }
}