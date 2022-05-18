package com.example.roomapp.fragments.user.bill

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomapp.R
import com.example.roomapp.model.Category
import com.example.roomapp.model.Product
import kotlinx.android.synthetic.main.custom_row_pdv.view.*
import java.math.RoundingMode
import java.text.DecimalFormat

class BillPdvAdapter: RecyclerView.Adapter<BillPdvAdapter.MyViewHolder>() {

    private var list = emptyList<Product>()
    private var list2 = emptyList<Category>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row_pdv, parent, false))
    }

    override fun getItemCount(): Int {
        return list2.size
    }

    var decim: DecimalFormat = DecimalFormat("#.##")
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem: Category = list2[position]
        holder.itemView.taxName.text = currentItem.pdvName
        holder.itemView.taxPercent.text = currentItem.pdv.toString()+"%"
        var totalWithNoTaxes = 0.00
        for(i in list){
            if(i.category==currentItem.nameCategory){
                var a=(i.quantity * i.price).toDouble()
                totalWithNoTaxes+=a/(1.00+currentItem.pdv.toDouble()/100)
            }
        }
        totalWithNoTaxes=totalWithNoTaxes.toBigDecimal().setScale(2, RoundingMode.DOWN).toDouble()
        holder.itemView.taxBase.text = totalWithNoTaxes.toString()

        var taxTot =totalWithNoTaxes*(currentItem.pdv.toDouble()/100)
        taxTot=taxTot.toBigDecimal().setScale(2, RoundingMode.DOWN).toDouble()
        holder.itemView.taxTotal.text = taxTot.toString()


    }

    fun setData(order: List<Product>, category: List<Category> ){
        this.list = order
        this.list2= category
        notifyDataSetChanged()
    }
}