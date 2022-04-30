package com.example.roomapp.fragments.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.roomapp.R
import com.example.roomapp.fragments.admin.users.list.ListFragmentDirections
import com.example.roomapp.model.Log
import com.example.roomapp.model.User
import com.example.roomapp.repository.UserRepository
import com.example.roomapp.viewmodel.LogViewModel
import com.example.roomapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class LoginFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var mLogViewModel: LogViewModel
    private lateinit var repository: UserRepository
    private lateinit var username: String
    private lateinit var password: String
    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        mLogViewModel = ViewModelProvider(this).get(LogViewModel::class.java)

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
        if (view.userNameTextField.text.isEmpty() || view.passwordTextField.text.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
        } else {
            uiScope.launch {
                val usersNames = repository.getUserName(username)
                if (usersNames != null) {
                    if(usersNames.lastName == password){
                        val cal: Calendar = Calendar.getInstance()
                        mLogViewModel.addLog(Log(0,username,"Logged in",cal.time.toString()))
                        view.userNameTextField.text = null
                        view.passwordTextField.text = null
                        if(usersNames.age == 0){
                            val action = LoginFragmentDirections.actionLoginFragmentToAdminFragment(usersNames)
                            findNavController().navigate(action)
                        }
                        else{
                            val action = LoginFragmentDirections.actionLoginFragmentToUserFragment(usersNames)
                            findNavController().navigate(action)
                        }
                    }else{
                        Toast.makeText(requireContext(), "Please check your Password", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "User doesnt exist!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}