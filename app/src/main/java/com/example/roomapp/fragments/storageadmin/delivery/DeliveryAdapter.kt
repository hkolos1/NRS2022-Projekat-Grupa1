package com.example.roomapp.fragments.storageadmin.delivery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.roomapp.R
import com.example.roomapp.model.Product
import com.example.roomapp.model.User
import kotlinx.android.synthetic.main.custom_row_delivery.view.*

class DeliveryAdapter: RecyclerView.Adapter<DeliveryAdapter.MyViewHolder>() {

    private var productList = emptyList<Product>()
    private var branchList = emptyList<String>()
    private lateinit var user: User

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_row_delivery, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem: Product = productList[position]
        holder.itemView.delivery_branch_name_txt.text = branchList[position]
        holder.itemView.delivery_product_name.text = currentItem.prodName
        holder.itemView.delivery_product_status.text = currentItem.deliveryStatus
        holder.itemView.delivery_product_quantity_txt.text = currentItem.quantity.toString()

        holder.itemView.rowLayout.setOnClickListener {
            val action = DeliveryFragmentDirections.actionDeliveryFragmentToDeliveryStatusFragment(currentItem,user,branchList[position])
            holder.itemView.findNavController().navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun setData(products: List<Product>, user: User, listBranch: MutableList<String>){
        this.productList = products
        this.user = user
        this.branchList = listBranch
        notifyDataSetChanged()
    }

}