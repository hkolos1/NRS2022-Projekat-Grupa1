package com.example.roomapp.fragments.storageadmin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomapp.R
import com.example.roomapp.model.Log
import com.example.roomapp.viewmodel.LogViewModel
import com.example.roomapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_admin.view.*
import kotlinx.android.synthetic.main.fragment_admin.view.textViewAdmin
import kotlinx.android.synthetic.main.fragment_admin_storage.*
import kotlinx.android.synthetic.main.fragment_admin_storage.view.*
import java.util.*

class StorageAdminFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var mLogViewModel: LogViewModel
    private val args by navArgs<StorageAdminFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin_storage, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        mLogViewModel = ViewModelProvider(this).get(LogViewModel::class.java)

        view.textViewAdmin.text = "Welcome ${args.user.firstName}"

        if(args.user.age==0){
            view.btn_logout4.visibility = INVISIBLE
        }

        view.btn_product.setOnClickListener {
            val action = StorageAdminFragmentDirections.actionStorageAdminFragmentToProductFragment(args.user)
            findNavController().navigate(action)
        }

        view.btn_delivery.setOnClickListener {
            val action = StorageAdminFragmentDirections.actionStorageAdminFragmentToDeliveryFragment(args.user)
            findNavController().navigate(action)
        }

        view.btn_branch.setOnClickListener {
            val action = StorageAdminFragmentDirections.actionStorageAdminFragmentToBranchFragment(args.user)
            findNavController().navigate(action)
        }

        view.btn_category.setOnClickListener {
            val action = StorageAdminFragmentDirections.actionStorageAdminFragmentToCategoryFragment(args.user)
            findNavController().navigate(action)
        }

        view.btn_logout4.setOnClickListener{
            logout()
        }

        return view
    }

    private fun logout(){
        val cal: Calendar = Calendar.getInstance()
        mLogViewModel.addLog(Log(0,args.user.firstName,"Logged out",cal.time.toString()))
        findNavController().navigate(R.id.action_storageAdminFragment_to_loginFragment)
    }

}