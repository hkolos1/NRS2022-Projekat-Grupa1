package com.example.roomapp.fragments.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomapp.R
import com.example.roomapp.fragments.login.LoginFragmentDirections
import com.example.roomapp.fragments.user.UserFragmentArgs
import com.example.roomapp.model.Log
import com.example.roomapp.viewmodel.LogViewModel
import com.example.roomapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_admin.view.*
import java.util.*

class AdminFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var mLogViewModel: LogViewModel
    private val args by navArgs<AdminFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        mLogViewModel = ViewModelProvider(this).get(LogViewModel::class.java)

        view.textViewAdmin.text = "Welcome ${args.user.firstName}"

        view.btn_users.setOnClickListener {
            goToUsers()
        }

        view.btn_storage.setOnClickListener {
            storage()
        }

        view.btn_logout3.setOnClickListener{
            logout()
        }

        view.btn_logging.setOnClickListener {
            logging()
        }

        return view
    }

    private fun logging() {
        findNavController().navigate(R.id.action_adminFragment_to_loggingFragment)
    }

    private fun logout(){
        val cal: Calendar = Calendar.getInstance()
        mLogViewModel.addLog(Log(0,args.user.firstName,"Logged out",cal.time.toString()))
        findNavController().navigate(R.id.action_adminFragment_to_loginFragment)
    }

    private fun storage(){
        val action = AdminFragmentDirections.actionAdminFragmentToStorageAdminFragment(args.user)
        findNavController().navigate(action)
    }

    private fun goToUsers() {
        val action = AdminFragmentDirections.actionAdminFragmentToListFragment(args.user)
        findNavController().navigate(action)
    }

}