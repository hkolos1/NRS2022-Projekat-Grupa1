package com.example.roomapp.fragments.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomapp.R
import com.example.roomapp.fragments.admin.users.update.UpdateFragmentArgs
import com.example.roomapp.model.Log
import com.example.roomapp.viewmodel.LogViewModel
import com.example.roomapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_user.view.*
import java.util.*

class UserFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var mLogViewModel: LogViewModel
    private val args by navArgs<UserFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        mLogViewModel = ViewModelProvider(this).get(LogViewModel::class.java)

        view.textViewUser.text = "Welcome ${args.user.firstName}"

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
        val cal: Calendar = Calendar.getInstance()
        mLogViewModel.addLog(Log(0,args.user.firstName,"Logged out",cal.time.toString()))
        findNavController().navigate(R.id.action_userFragment_to_loginFragment)
    }

}