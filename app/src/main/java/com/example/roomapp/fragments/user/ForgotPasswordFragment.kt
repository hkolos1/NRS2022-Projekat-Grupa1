package com.example.roomapp.fragments.user

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomapp.R
import com.example.roomapp.fragments.login.LoginFragmentDirections
import com.example.roomapp.model.Log
import com.example.roomapp.repository.UserRepository
import com.example.roomapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_forgot_password.view.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class ForgotPasswordFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var username: String
    private lateinit var repository: UserRepository
    private val args by navArgs<ForgotNewPasswordFragmentArgs>()
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_forgot_password, container, false)


        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        repository = mUserViewModel.repository

        view.btn_nextForgotPassword.setOnClickListener {
            forgotPasswordButton(view)
        }


        view.userNameForgotPassword.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                username = s.toString()
            }
        })

        return view
    }

    private fun forgotPasswordButton(view: View) {

        if (view.userNameForgotPassword.text.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter username", Toast.LENGTH_SHORT).show()
        } else {
            uiScope.launch {
                val usersNames = repository.getUserName(username)
                if (usersNames != null) {
                    if(usersNames.answer==""){
                        Toast.makeText(requireContext(), "Please contact Admin for password change", Toast.LENGTH_LONG).show()
                    }else {
                        val action =
                            ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToForgotPasswordQuestionFragment(
                                usersNames
                            )
                        findNavController().navigate(action)
                    }
                } else {
                    Toast.makeText(requireContext(), "User doesnt exist!", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

}