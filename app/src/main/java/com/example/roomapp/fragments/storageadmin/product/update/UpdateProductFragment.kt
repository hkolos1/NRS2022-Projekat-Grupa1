package com.example.roomapp.fragments.storageadmin.product.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomapp.R
import com.example.roomapp.model.Log
import com.example.roomapp.model.Product
import com.example.roomapp.viewmodel.CategoryViewModel
import com.example.roomapp.viewmodel.LogViewModel
import com.example.roomapp.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_update_product.*
import kotlinx.android.synthetic.main.fragment_update_product.view.*
import java.util.*


class UpdateProductFragment : Fragment() {
    private val args by navArgs<UpdateProductFragmentArgs>()
    private lateinit var mLogViewModel: LogViewModel
    private lateinit var  mProductViewModel: ProductViewModel
    private lateinit var mCategoryViewModel: CategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_update_product, container, false)

        mProductViewModel=ViewModelProvider(this).get(ProductViewModel::class.java)
        mLogViewModel = ViewModelProvider(this).get(LogViewModel::class.java)
        mCategoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)


        view.updatePrName.setText(args.currentProduct.prodName)

        view.updatePrAmount.setText(args.currentProduct.quantity.toString())

        view.updatePrUnit.setText(args.currentProduct.unit)

        view.updatePrPrice.setText(args.currentProduct.price.toString())

        val category = view.spinnerCategoryUpdate

        val spinnerProdAdapter = ArrayAdapter<Any>(requireContext(), android.R.layout.simple_spinner_dropdown_item)

        mCategoryViewModel.readAllData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                categ -> categ.forEach {
            spinnerProdAdapter.add(it.nameCategory)
        }
        })

        category.adapter = spinnerProdAdapter

        view.updateButton.setOnClickListener {
            updateItem()
        }
        setHasOptionsMenu(true)

        return view
    }

    private fun updateItem(){
        val prName=updatePrName.text.toString()
        val prDelStat=args.currentProduct.deliveryStatus
        val quan=updatePrAmount.text.toString().toInt()
        val unit=updatePrUnit.text.toString()
        val cat = spinnerCategoryUpdate.selectedItem

        val price=updatePrPrice.text.toString().toLong()

        if(inputCheck(prName, prDelStat, updatePrAmount.text.toString())){
            val updatedProduct= Product(args.currentProduct.id, prName, quan,unit,args.currentProduct.branchId,prDelStat,
                cat.toString(),price)
            mProductViewModel.updateProduct(updatedProduct)
            val cal: Calendar = Calendar.getInstance()
            mLogViewModel.addLog(Log(0,args.user.firstName,"Updated product ${args.currentProduct.prodName}",cal.time.toString()))

            Toast.makeText(requireContext(),"Product updated", Toast.LENGTH_LONG).show()
            findNavController().navigateUp()
        }else{
            Toast.makeText(requireContext(),"Please fill out all fields! ", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(prName: String, prDevStat: String, quan: String): Boolean{
        return !(TextUtils.isEmpty(prName) && TextUtils.isEmpty(prDevStat) && quan.isEmpty() )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_product_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete_prod) {
            deleteProduct()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteProduct() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mProductViewModel.deleteProduct(args.currentProduct)
            val cal: Calendar = Calendar.getInstance()
            mLogViewModel.addLog(Log(0,args.user.firstName,"Deleted product ${args.currentProduct.prodName}",cal.time.toString()))

            Toast.makeText(
                requireContext(),
                "Successfully removed: ${args.currentProduct.prodName}",
                Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${args.currentProduct.prodName}?")
        builder.setMessage("Are you sure you want to delete ${args.currentProduct.prodName}?")
        builder.create().show()
    }
}