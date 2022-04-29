package com.example.roomapp.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.roomapp.R
import com.example.roomapp.model.User
import com.example.roomapp.repository.UserRepository
import com.example.roomapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_login.view.*

class LoginFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var repository: UserRepository
    private lateinit var username: String
    private lateinit var password: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        repository = mUserViewModel.repository

        view.submitButton.setOnClickListener {
            loginButton(view)
        }

        view.userNameTextField.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                username= s.toString()
            }
        })


        view.passwordTextField.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                password= s.toString()
            }
        })

        return view
    }

    private fun loginButton(view: View) {
        if (username == "" || password == "") {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
        } else {
            mUserViewModel.getUser(username)
                val usersNames = mUserViewModel.currentUser
                if (usersNames != null) {
                    if(usersNames.lastName == password){
                        view.userNameTextField.text = null
                        view.passwordTextField.text = null
                        navigateUserDetails(usersNames)
                    }else{
                        Toast.makeText(requireContext(), "Please check your Password", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Press LOGIN again,please.", Toast.LENGTH_SHORT).show()
                }
        }
    }
        //Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
        //Toast.makeText(requireContext(), "User doesnt exist,please Register!", Toast.LENGTH_SHORT).show()
        //Toast.makeText(requireContext(), "Please check your Password", Toast.LENGTH_SHORT).show()
        //navigateUserDetails()

    private fun navigateUserDetails(user: User) {
        if(user.age == 0)
            findNavController().navigate(R.id.action_loginFragment_to_adminFragment)
        else
            findNavController().navigate(R.id.action_loginFragment_to_userFragment)
    }

}