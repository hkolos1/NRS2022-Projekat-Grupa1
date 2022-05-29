package com.example.roomapp.fragments.storageadmin.branch.places

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.roomapp.R
import com.example.roomapp.model.Branch
import com.example.roomapp.model.User
import com.example.roomapp.viewmodel.BranchViewModel
import kotlinx.android.synthetic.main.custom_row_branch_place.view.*
import kotlinx.android.synthetic.main.custom_row_branch_product.view.*
import kotlinx.android.synthetic.main.custom_row_product.view.*

class AllPlacesAdapter: RecyclerView.Adapter<AllPlacesAdapter.MyViewHolder>()  {

    private var productsList = emptyList<String>()
    private lateinit var user: User
    private lateinit var branch : Branch
    private lateinit var mBranchViewModel: BranchViewModel

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_row_branch_place, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {


        val currentItem: String = productsList[position]

        holder.itemView.all_places_name.text = currentItem

        holder.itemView.buttonDeletePlace.setOnClickListener{
            branch.places.forEach {
                if(currentItem == it){
                    branch.places.remove(it)

                    mBranchViewModel.updateBranch(branch)
                    setData(productsList, user, branch, mBranchViewModel)
                }
            }
        }

    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    fun setData(
        products: List<String>,
        user: User,
        branch: Branch,
        mBranchViewModel: BranchViewModel
    ){
        this.productsList = products
        this.user = user
        this.branch = branch
        this.mBranchViewModel = mBranchViewModel
        notifyDataSetChanged()
    }

}