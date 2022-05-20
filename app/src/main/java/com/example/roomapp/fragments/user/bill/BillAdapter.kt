package com.example.roomapp.fragments.user.bill

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomapp.R
import com.example.roomapp.model.Product
import kotlinx.android.synthetic.main.custom_row_bill.view.*
import java.math.RoundingMode
import java.text.DecimalFormat

class BillAdapter: RecyclerView.Adapter<BillAdapter.MyViewHolder>() {

    private var list = emptyList<Product>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row_bill, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem: Product = list[position]
        holder.itemView.prNameBill.text = currentItem.prodName
        val df = DecimalFormat("0.000")
        df.roundingMode = RoundingMode.CEILING
        holder.itemView.prQuan.text = "Quantity:  ${currentItem.quantity}"
        holder.itemView.prOnePrice.text = "Price per one: ${currentItem.price}"
        holder.itemView.prTotal.text = "Total: ${currentItem.quantity * currentItem.price}"

    }

    fun setData(order: List<Product>){
        this.list = order
        notifyDataSetChanged()
    }
}


