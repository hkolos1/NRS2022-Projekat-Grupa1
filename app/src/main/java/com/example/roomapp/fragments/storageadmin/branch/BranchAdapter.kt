package com.example.roomapp.fragments.storageadmin.branch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.roomapp.R
import com.example.roomapp.model.Branch
import kotlinx.android.synthetic.main.custom_row_branch.view.*
import kotlinx.android.synthetic.main.custom_row_branch.view.rowLayout

class BranchAdapter: RecyclerView.Adapter<BranchAdapter.MyViewHolder>()  {

    private var branchList = emptyList<Branch>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_row_branch, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem: Branch = branchList[position]

        holder.itemView.branch_id_txt.text = currentItem.id.toString()
        holder.itemView.branch_name_txt.text = currentItem.name


        holder.itemView.rowLayout.setOnClickListener {
            val action = BranchFragmentDirections.actionBranchFragmentToBranchChooserFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return branchList.size
    }

    fun setData(branches: List<Branch>){
        this.branchList = branches
        notifyDataSetChanged()
    }
}