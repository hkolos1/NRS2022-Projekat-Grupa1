package com.example.roomapp.fragments.user.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomapp.R
import com.example.roomapp.model.Branch
import com.example.roomapp.model.Log
import com.example.roomapp.model.Order
import com.example.roomapp.model.Product
import com.example.roomapp.viewmodel.BranchViewModel
import com.example.roomapp.viewmodel.LogViewModel
import com.example.roomapp.viewmodel.OrderViewModel
import com.example.roomapp.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_order_add_product.*
import kotlinx.android.synthetic.main.fragment_order_add_product.view.*
import java.util.*

class AddProductToOrderFragment : Fragment() {

    private lateinit var mBranchViewModel: BranchViewModel
    private lateinit var mOrderViewModel: OrderViewModel
    private lateinit var mLogViewModel: LogViewModel
    private lateinit var mProductViewModel: ProductViewModel
    private lateinit var mainBranch: Branch
    private val args by navArgs<AddProductToOrderFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_order_add_product, container, false)

        mBranchViewModel = ViewModelProvider(this).get(BranchViewModel::class.java)
        mOrderViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)
        mLogViewModel = ViewModelProvider(this).get(LogViewModel::class.java)
        mProductViewModel=ViewModelProvider(this).get(ProductViewModel::class.java)

        val spinnerProducts = view.spinner_assign_product_product_order

        val spinnerProdAdapter = ArrayAdapter<Any>(requireContext(), android.R.layout.simple_spinner_dropdown_item)

        mBranchViewModel.readAllData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            branch -> branch.forEach{ branch1 ->
            if(branch1.name == args.order.branch) {
                    mainBranch = branch1
                (branch1.products).forEach {
                    var da = true
                    args.order.products.forEach { orPro ->
                        if(orPro.prodName == it.prodName){
                            da = false
                        }
                    }
                    if(da) spinnerProdAdapter.add(it)
                }
            }
        }
        })

        spinnerProducts.adapter = spinnerProdAdapter

        view.btn_assign_product_order.setOnClickListener{
            val chosenProduct: Product = spinnerProducts.selectedItem as Product
            val chosenOrder: Order = args.order
            val quantity = edit_assign_product_quantity_order.text.toString().toInt()

            if(quantity>chosenProduct.quantity){
                Toast.makeText(requireContext(), "Not valid quantity for "+chosenProduct.prodName, Toast.LENGTH_SHORT).show()
            }else{
                args.order.total += quantity * chosenProduct.price
                args.order.productsQuantity+=1
                args.order.products.add(
                    Product(chosenProduct.id,chosenProduct.prodName,quantity,
                        chosenProduct.unit,chosenProduct.branchId,chosenProduct.deliveryStatus,
                        chosenProduct.category,chosenProduct.price)
                )
                mOrderViewModel.updateOrder(args.order)

                mainBranch.products[mainBranch.products.indexOf(chosenProduct)].quantity-= quantity
                mBranchViewModel.updateBranch(mainBranch)

                val cal: Calendar = Calendar.getInstance()
                mLogViewModel.addLog(Log(0,args.user.firstName,"Assigned ${chosenProduct.prodName} to ${chosenOrder.name}",cal.time.toString()))
                Toast.makeText(requireContext(), "Assigned " + chosenProduct.prodName + " to " + chosenOrder.name + " successfully!", Toast.LENGTH_SHORT).show()

                findNavController().navigateUp()
            }
        }

        return view
    }

}