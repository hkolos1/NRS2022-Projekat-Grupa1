package com.example.roomapp.fragments.storageadmin.delivery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.roomapp.R
import com.example.roomapp.fragments.storageadmin.branch.BranchFragmentDirections
import com.example.roomapp.model.Product
import kotlinx.android.synthetic.main.custom_row_branch.view.*
import kotlinx.android.synthetic.main.custom_row_delivery.view.*
import kotlinx.android.synthetic.main.custom_row_delivery.view.rowLayout

class DeliveryAdapter: RecyclerView.Adapter<DeliveryAdapter.MyViewHolder>() {

    private var productList = emptyList<Product>()

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

        holder.itemView.delivery_product_name.text = currentItem.prodName
        holder.itemView.delivery_product_status.text = currentItem.deliveryStatus

        holder.itemView.rowLayout.setOnClickListener {
            val action = DeliveryFragmentDirections.actionDeliveryFragmentToDeliveryStatusFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return productList.size
    }

    fun setData(products: List<Product>){
        this.productList = products
        notifyDataSetChanged()
    }

}