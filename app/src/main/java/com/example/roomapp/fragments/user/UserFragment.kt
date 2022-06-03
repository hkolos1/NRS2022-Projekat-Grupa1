package com.example.roomapp.fragments.user

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomapp.R
import com.example.roomapp.model.Log
import com.example.roomapp.viewmodel.LogViewModel
import com.example.roomapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_user.view.*
import java.util.*

class UserFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var mLogViewModel: LogViewModel
    private val args by navArgs<UserFragmentArgs>()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user, container, false)
        passStatus(view)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        mLogViewModel = ViewModelProvider(this).get(LogViewModel::class.java)

        view.textViewUser.text = "Welcome ${args.user.firstName} \n ${args.user.branch}"
        view.refreshDrawableState()


        view.btn_password.setOnClickListener {
            changePassword()
        }

        view.btn_orders.setOnClickListener {
            orders()
        }

        view.btn_logout2.setOnClickListener{
            logout()
        }

        return view
    }

    private fun changePassword(){
        if(args.user.question.isEmpty() && args.user.answer.isEmpty()){
            val action = UserFragmentDirections.actionUserFragmentToConfirmationPasswordFragment(args.user)
            findNavController().navigate(action)
        }else{
            //findNavController().navigate(R.id.action_userFragment_to_updatePasswordFragment)
            val action = UserFragmentDirections.actionUserFragmentToUpdatePasswordFragment(args.user)
            findNavController().navigate(action)
        }
    }

    private fun orders(){
        val action = UserFragmentDirections.actionUserFragmentToOrderFragment(args.user)
        findNavController().navigate(action)
    }

    private fun logout() {
        val cal: Calendar = Calendar.getInstance()
        mLogViewModel.addLog(Log(0,args.user.firstName,"Logged out",cal.time.toString()))
        findNavController().navigate(R.id.action_userFragment_to_loginFragment)
    }

    @SuppressLint("SetTextI18n")
    private fun passStatus(view: View){
        if(args.user.question.isEmpty() && args.user.answer.isEmpty()){
            view.btn_password.text = "confirm password"
        }else view.btn_password.text = "change password"
    }

}