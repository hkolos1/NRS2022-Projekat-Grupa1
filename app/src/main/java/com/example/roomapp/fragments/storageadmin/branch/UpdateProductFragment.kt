package com.example.roomapp.fragments.storageadmin.branch

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
import java.util.*
import kotlin.concurrent.thread

class UpdateProductFragment: Fragment() {

    private lateinit var mProductViewModel: ProductViewModel
    private lateinit var mBranchViewModel: BranchViewModel
    private lateinit var mLogViewModel: LogViewModel
    private val args by navArgs<UpdateProductFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update_product_in_branch, container, false)

        val chosenBranch = args.currentBranch
        val chosenProduct = args.currentProduct

        view.textViewBranchUpdateProduct.text = chosenBranch.name
        view.text_view_update_product_name.text = chosenProduct.prodName


        mProductViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        mBranchViewModel = ViewModelProvider(this).get(BranchViewModel::class.java)
        mLogViewModel = ViewModelProvider(this).get(LogViewModel::class.java)

        view.btn_update_product_in_branch.setOnClickListener{
            thread{
                val prodInWarehouse = mProductViewModel.getProductById(chosenProduct.id)

                val newQuantity = view.update_product_quant.text.toString().toInt()

                val combinedQuantity = prodInWarehouse.quantity+chosenProduct.quantity
                if(newQuantity > combinedQuantity){
                    requireActivity().runOnUiThread {
                        Toast.makeText(activity, "Maximum assignable: $combinedQuantity" +
                                "\nAvailable in warehouse ${prodInWarehouse.quantity}, currently in branch ${chosenProduct.quantity}", Toast.LENGTH_SHORT).show()
                    }
                }else{

                    //Ukoliko se samo smanjuje trenutna kolicina, onda se samo oduzima quantity i updatuje product u branchu
                    if(newQuantity <= chosenProduct.quantity){
                        addLog(chosenProduct, newQuantity)
                        val newProduct = Product(chosenProduct.id, chosenProduct.prodName, newQuantity, chosenProduct.unit, chosenBranch.id, chosenProduct.deliveryStatus,
                            chosenProduct.category, chosenProduct.price)

                        chosenBranch.products.remove(chosenProduct)
                        chosenBranch.products.add(newProduct)

                        mBranchViewModel.updateBranch(chosenBranch)

                    }


                    //Ukoliko se dodaje veca kolicina od trenutne u branchu
                    // , onda se oduzima potrebni dio iz skladista i dodaje trenutnoj vrijednosti
                    if(newQuantity > chosenProduct.quantity){
                        addLog(chosenProduct, newQuantity)
                        val newProduct = Product(chosenProduct.id, chosenProduct.prodName, prodInWarehouse.quantity-(newQuantity-chosenProduct.quantity),
                            chosenProduct.unit, chosenProduct.id, chosenProduct.deliveryStatus, chosenProduct.category, chosenProduct.price)

                        mProductViewModel.updateProduct(newProduct)


                        chosenBranch.products.remove(chosenProduct)
                        chosenBranch.products.add(Product(chosenProduct.id,chosenProduct.prodName,newQuantity,
                            chosenProduct.unit,chosenBranch.id,chosenProduct.deliveryStatus,
                            chosenProduct.category,chosenProduct.price))
                        mBranchViewModel.updateBranch(chosenBranch)

                    }
                }

            }
        }

        return view
    }

    fun addLog(prod: Product, quantity: Int){
        val cal: Calendar = Calendar.getInstance()
        mLogViewModel.addLog(Log(0,"Storage Admin","Updated ${prod.prodName} to $quantity",cal.time.toString()))
    }


}