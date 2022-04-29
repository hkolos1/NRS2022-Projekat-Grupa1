package com.example.roomapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.roomapp.R
import com.example.roomapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_admin.view.*

class AdminFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.textViewAdmin.text = "Welcome Admin"

        view.btn_users.setOnClickListener {
            goToUsers()
        }

        view.btn_password2.setOnClickListener {
            changePassword()
        }

        view.btn_logout3.setOnClickListener{
            logout()
        }

        return view
    }

    private fun logout(){
        findNavController().navigate(R.id.action_adminFragment_to_loginFragment)
    }

    private fun changePassword(){
        findNavController().navigate(R.id.action_adminFragment_to_updatePasswordFragment)
    }

    private fun goToUsers() {
        findNavController().navigate(R.id.action_adminFragment_to_listFragment)
    }

}