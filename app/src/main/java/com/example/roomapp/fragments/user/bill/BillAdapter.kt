package com.example.roomapp.fragments.user.bill

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

    private var list = emptyList<Product>() //lista svih proizvoda iz narudzbe
    private var list1 = emptyList<Int>() //lista svih pdv-a
    //tako da prvi u list je prvi proizvod a prvi element list1 je pdv za taj proizvod

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row_bill, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem: Product = list[position]
        holder.itemView.prNameBill.text = currentItem.prodName
        val df = DecimalFormat("0.000")
        df.roundingMode = RoundingMode.CEILING
        val crQuan=df.format(currentItem.quantity.toDouble()).toDouble()
        holder.itemView.prQuan.text = crQuan.toString()
        holder.itemView.prOnePrice.text = currentItem.price.toString()
        holder.itemView.prTotal.text = (currentItem.quantity*currentItem.price).toString()

    }

    fun setData(order: List<Product>,taxes : List<Int>){
        this.list = order
        this.list1 = taxes
        notifyDataSetChanged()
    }
}


