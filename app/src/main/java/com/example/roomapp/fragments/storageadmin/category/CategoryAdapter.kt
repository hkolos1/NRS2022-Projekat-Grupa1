package com.example.roomapp.fragments.storageadmin.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.roomapp.R
import com.example.roomapp.model.Category
import com.example.roomapp.model.User
import kotlinx.android.synthetic.main.custom_row_category.view.*
import kotlinx.android.synthetic.main.custom_row_product.view.rowLayout2

class CategoryAdapter: RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {

    private var list = emptyList<Category>()
    private lateinit var user : User

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row_category, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem: Category = list[position]
        holder.itemView.idCategory.text = (position+1).toString()
        holder.itemView.categoryName.text = currentItem.nameCategory
        holder.itemView.pdv_tax.text = currentItem.pdvName
        holder.itemView.pdvX.text = currentItem.pdv.toString()

       holder.itemView.rowLayout2.setOnClickListener{
           val action=CategoryFragmentDirections.actionCategoryFragmentToUpdateCategoryFragment(currentItem,user)
           holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(category: List<Category>, user: User){
        this.list = category
        this.user = user
        notifyDataSetChanged()
    }
}
