package com.example.roomapp.fragments.user.orders

import android.annotation.SuppressLint
import android.graphics.Color
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.roomapp.R
import com.example.roomapp.model.Order
import com.example.roomapp.model.Product
import kotlinx.android.synthetic.main.custom_row_order_product.view.*
import kotlinx.android.synthetic.main.fragment_order_update.view.*
import kotlinx.android.synthetic.main.item_product.view.prName
import java.math.RoundingMode

class UpdateOrderAdapter: RecyclerView.Adapter<UpdateOrderAdapter.MyViewHolder>() {

    private var list = emptyList<Product>()
    private var products = emptyList<Product>()
    private var quantity = 0
    private lateinit var order: Order
    private lateinit var total: TextView


    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row_order_product, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem: Product = list[position]
        holder.itemView.prName.text = currentItem.prodName
        holder.itemView.kolicina.text = currentItem.quantity.toString()
        holder.itemView.perunit.text = "Per ${currentItem.unit}: ${currentItem.price}"
        holder.itemView.totalProd.text = "Total: ${(currentItem.price*currentItem.quantity).toBigDecimal().setScale(2, RoundingMode.UP).toDouble()}"
        if(order.bill){
            holder.itemView.buttonX.visibility = INVISIBLE
            holder.itemView.buttonplus.visibility = INVISIBLE
            holder.itemView.buttonminus.visibility = INVISIBLE
        }
        holder.itemView.buttonminus.setOnClickListener {
            quantity = Integer.parseInt(holder.itemView.kolicina.text.toString())
            if(quantity<=0){
                quantity=0
            }else{
                quantity--
                holder.itemView.kolicina.text = "$quantity"
            }
            order.total -= (currentItem.price*currentItem.quantity).toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
            currentItem.quantity = quantity
            order.total += (currentItem.price*quantity).toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
            total.text = order.total.toString()

            holder.itemView.totalProd.text = "Total: ${(currentItem.price*currentItem.quantity).toBigDecimal().setScale(2, RoundingMode.UP).toDouble()}"
        }

        holder.itemView.buttonplus.setOnClickListener {
            quantity = Integer.parseInt(holder.itemView.kolicina.text.toString())
            if(quantity>=products[position].quantity){
                quantity=products[position].quantity
            }else{
                quantity++
                holder.itemView.kolicina.text = "$quantity"
            }
            order.total -= (currentItem.price*currentItem.quantity).toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
            currentItem.quantity = quantity
            order.total += (currentItem.price*quantity).toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
            total.text = order.total.toString()

            holder.itemView.totalProd.text = "Total: ${(currentItem.price*currentItem.quantity).toBigDecimal().setScale(2, RoundingMode.UP).toDouble()}"
        }

        holder.itemView.buttonX.setOnClickListener {
            order.products.remove(currentItem)
            order.productsQuantity--
            order.total -= (currentItem.price*currentItem.quantity).toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
            total.text = order.total.toString()
            setData(order.products, products, order, total)
        }
    }

    fun setData(list: List<Product>, products: List<Product>, order: Order, total: TextView){
        this.list = list
        this.products = products
        this.order = order
        this.total = total
        notifyDataSetChanged()
    }
}
