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
import kotlinx.android.synthetic.main.fragment_user.view.*

class UserFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.textViewUser.text = "Welcome User"

        view.btn_password.setOnClickListener {
            changePassword()
        }

        view.btn_logout2.setOnClickListener{
            logout()
        }

        return view
    }

    private fun changePassword(){
        findNavController().navigate(R.id.action_userFragment_to_updatePasswordFragment)
    }

    private fun logout() {
        findNavController().navigate(R.id.action_userFragment_to_loginFragment)
    }

}