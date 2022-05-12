package com.example.roomapp.fragments.user.bill

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomapp.R
import com.example.roomapp.model.Order
import com.example.roomapp.model.User
import kotlinx.android.synthetic.main.custom_row_bill.view.*
import kotlinx.android.synthetic.main.custom_row_product.view.*

class BillAdapter: RecyclerView.Adapter<BillAdapter.MyViewHolder>() {

    private var list = emptyList<Order>()
    private lateinit var user : User

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row_bill, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem: Order = list[position]
        holder.itemView.prNameBill.text = currentItem.products[position].prodName
        holder.itemView.prQuanBill.text = currentItem.id.toString()
        holder.itemView.prPrice.text = currentItem.products[position].quantity.toString()
        holder.itemView.prTotal.text = currentItem.total.toString()

        holder.itemView.rowLayout2.setOnClickListener{
            //val action=OrderFragmentDirections.actionOrderFragmentToUpdateOrderFragment(user,currentItem)
            //holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(order: List<Order>, user: User){
        this.list = order
        this.user = user
        notifyDataSetChanged()
    }
}
