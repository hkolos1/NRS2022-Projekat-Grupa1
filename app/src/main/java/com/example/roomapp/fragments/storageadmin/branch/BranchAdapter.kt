package com.example.roomapp.fragments.storageadmin.branch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.roomapp.R
import com.example.roomapp.model.Branch
import com.example.roomapp.model.User
import kotlinx.android.synthetic.main.custom_row_branch.view.*

class BranchAdapter: RecyclerView.Adapter<BranchAdapter.MyViewHolder>()  {

    private var branchList = emptyList<Branch>()
    private lateinit var user: User

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_row_branch, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem: Branch = branchList[position]


        holder.itemView.mat3_custom_row_branch_name.text = currentItem.name
        if(currentItem.products.size == 1)
            holder.itemView.mat3_custom_row_product_amount_in_branch.text = currentItem.products.size.toString() + " Product"
        else
            holder.itemView.mat3_custom_row_product_amount_in_branch.text = currentItem.products.size.toString() + " Products"

        holder.itemView.rowLayout.setOnClickListener {
            val action = BranchFragmentDirections.actionBranchFragmentToAllProductsInBranchFragment(currentItem,user)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return branchList.size
    }

    fun setData(branches: List<Branch>, user: User){
        this.branchList = branches
        this.user = user
        notifyDataSetChanged()
    }
}