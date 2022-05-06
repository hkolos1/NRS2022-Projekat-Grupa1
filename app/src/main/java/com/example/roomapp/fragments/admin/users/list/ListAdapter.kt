package com.example.roomapp.fragments.admin.users.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.roomapp.R
import com.example.roomapp.model.User
import kotlinx.android.synthetic.main.custom_row.view.*

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    private var userList = emptyList<User>()
    private lateinit var user: User

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false))
    }

    override fun getItemCount(): Int {
       return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem: User = userList[position]
        holder.itemView.id_txt.text = (position+1).toString()
        holder.itemView.firstName_txt.text = currentItem.firstName
        holder.itemView.lastName_txt.text = currentItem.lastName
        var role = "User"
        if(currentItem.age==0) {
            role = "Admin"
        }
        else if(currentItem.age==1) {
            role = "Storage Admin"
        }

        holder.itemView.age_txt.text = "($role)"

        holder.itemView.rowLayout.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem,user)
            holder.itemView.findNavController().navigate(action)
        }
    }

    fun setData(user: List<User>, user1: User){
        this.userList = user
        this.user = user1
        notifyDataSetChanged()
    }
}