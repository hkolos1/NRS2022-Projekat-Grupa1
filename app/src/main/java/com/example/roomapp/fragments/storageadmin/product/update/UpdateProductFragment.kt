package com.example.roomapp.fragments.storageadmin.product.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomapp.R
import com.example.roomapp.model.Product
import com.example.roomapp.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_update_product.*
import kotlinx.android.synthetic.main.fragment_update_product.view.*


class UpdateProductFragment : Fragment() {
      private val args by navArgs<UpdateProductFragmentArgs>()

    private lateinit var  mProductViewModel: ProductViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_update_product, container, false)

        mProductViewModel=ViewModelProvider(this).get(ProductViewModel::class.java)

        view.updatePrName.setText(args.currentProduct.prodName)
        view.updatePrCode.setText(args.currentProduct.deliveryStatus)
        view.updatePrAmount.setText(args.currentProduct.quantity.toString())

        view.updateButton.setOnClickListener {
            updateItem()
        }
        setHasOptionsMenu(true)

        return view
    }

    private fun updateItem(){
        val prName=updatePrName.text.toString()
        val prDelStat=updatePrCode.text.toString()
        val quan=updatePrAmount.text.toString().toInt()

        if(inputCheck(prName, prDelStat, updatePrAmount.text)){
            val updatedProduct= Product(args.currentProduct.id, prName, quan,args.currentProduct.branchId,prDelStat)
            mProductViewModel.updateProduct(updatedProduct)
            Toast.makeText(requireContext(),"Product updated", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateProductFragment_to_productFragment)
        }else{
            Toast.makeText(requireContext(),"Please fill out all fields! ", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(prName: String, prDevStat: String, quan: Editable): Boolean{
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
            Toast.makeText(
                requireContext(),
                "Successfully removed: ${args.currentProduct.prodName}",
                Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateProductFragment_to_productFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${args.currentProduct.prodName}?")
        builder.setMessage("Are you sure you want to delete ${args.currentProduct.prodName}?")
        builder.create().show()
    }
}