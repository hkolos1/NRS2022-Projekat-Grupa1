package com.example.roomapp.fragments.storageadmin.branch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.roomapp.R
import com.example.roomapp.fragments.storageadmin.category.CategoryFragmentDirections
import com.example.roomapp.model.Branch
import com.example.roomapp.model.Product
import com.example.roomapp.model.User
import kotlinx.android.synthetic.main.custom_row_branch_product.view.*
import kotlinx.android.synthetic.main.custom_row_product.view.*

class AllProductsAdapter: RecyclerView.Adapter<AllProductsAdapter.MyViewHolder>()  {

    private var productsList = emptyList<Product>()
    private lateinit var user: User
    private lateinit var branch : Branch

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_row_branch_product, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem: Product = productsList[position]

        holder.itemView.all_products_name.text = currentItem.prodName
        holder.itemView.all_products_quantity_and_unit.text = currentItem.quantity.toString() + " " + currentItem.unit.toString()

        holder.itemView.rowLayout23.setOnClickListener{
            val action= AllProductsFragmentDirections.actionAllProductsInBranchFragmentToAddProductsToUpdateProductsFragment(branch, currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    fun setData(products: List<Product>, user: User, branch: Branch){
        this.productsList = products
        this.user = user
        this.branch = branch
        notifyDataSetChanged()
    }

}