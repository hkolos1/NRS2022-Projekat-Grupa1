package com.example.roomapp.fragments.storageadmin.branch.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomapp.R
import com.example.roomapp.model.Log
import com.example.roomapp.model.Product
import com.example.roomapp.viewmodel.BranchViewModel
import com.example.roomapp.viewmodel.LogViewModel
import com.example.roomapp.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_update_product_in_branch.view.*
import java.math.RoundingMode
import java.util.*
import kotlin.concurrent.thread

class UpdateProductFragment: Fragment() {

    private lateinit var mProductViewModel: ProductViewModel
    private lateinit var mBranchViewModel: BranchViewModel
    private lateinit var mLogViewModel: LogViewModel
    private val args by navArgs<UpdateProductFragmentArgs>()
    private lateinit var chosenProduct: Product


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update_product_in_branch, container, false)

        mProductViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        mBranchViewModel = ViewModelProvider(this).get(BranchViewModel::class.java)
        mLogViewModel = ViewModelProvider(this).get(LogViewModel::class.java)

        val chosenBranch = args.currentBranch

        mProductViewModel.readAllData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            product -> product.forEach {
                if(it.prodName==args.currentProduct.prodName)
                    chosenProduct = it
            }
        })

        view.textViewBranchUpdateProduct.text = chosenBranch.name
        view.text_view_update_product_name.text = args.currentProduct.prodName

        view.btn_update_product_in_branch.setOnClickListener{
                var newQuantity = view.update_product_quant.text.toString().toDouble()
            if(chosenProduct.round==false){
                newQuantity=newQuantity.toInt().toDouble()
            }
                newQuantity=newQuantity.toBigDecimal().setScale(3, RoundingMode.UP).toDouble()
                val combinedQuantity = chosenProduct.quantity+args.currentProduct.quantity
                if(newQuantity > combinedQuantity){
                    Toast.makeText(context, "Maximum assignable: $combinedQuantity" +
                            "\nAvailable in warehouse ${chosenProduct.quantity}, " +
                            "currently in branch ${args.currentProduct.quantity}", Toast.LENGTH_LONG).show()
                }else{
                    val prod =chosenBranch.products[chosenBranch.products.indexOf(args.currentProduct)]
                    prod.quantity = newQuantity
                    prod.deliveryStatus ="Unassigned"

                    mBranchViewModel.updateBranch(chosenBranch)

                    chosenProduct.quantity=combinedQuantity-newQuantity
                    mProductViewModel.updateProduct(chosenProduct)

                    val cal: Calendar = Calendar.getInstance()
                    mLogViewModel.addLog(Log(0,"Storage Admin","Updated ${chosenProduct.prodName} to $newQuantity",cal.time.toString()))

                    findNavController().navigateUp()
                }
        }

        return view
    }



}