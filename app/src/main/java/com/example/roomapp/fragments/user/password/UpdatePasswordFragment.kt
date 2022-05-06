package com.example.roomapp.fragments.user.password

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
import androidx.navigation.fragment.navArgs
import com.example.roomapp.R
import com.example.roomapp.model.Log
import com.example.roomapp.model.User
import com.example.roomapp.viewmodel.LogViewModel
import com.example.roomapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_update_password.view.*
import java.util.*

class UpdatePasswordFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var oldPassword: String
    private lateinit var newPassword: String
    private lateinit var confPassword: String
    private val args by navArgs<UpdatePasswordFragmentArgs>()
    private lateinit var mLogViewModel: LogViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update_password, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        mLogViewModel = ViewModelProvider(this).get(LogViewModel::class.java)

        view.oldPassword.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                oldPassword = s.toString()
            }
        })

        view.newPassword.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                newPassword = s.toString()
            }
        })

        view.confirmPassword.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                confPassword = s.toString()
            }
        })

        view.btn_chang_pass.setOnClickListener {
            changPasswordButton(view)
        }

        return view
    }

    private fun changPasswordButton(view: View) {
        if (view.oldPassword.text.isEmpty() || view.newPassword.text.isEmpty() || view.confirmPassword.text.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
        } else if (oldPassword == args.user.lastName && newPassword == confPassword){
            val updatedUser = User(args.user.id , args.user.firstName , newPassword , args.user.age ,args.user.question,args.user.answer)
            mUserViewModel.updateUser(updatedUser)
            val cal: Calendar = Calendar.getInstance()
            mLogViewModel.addLog(Log(0,args.user.firstName,"Password changed",cal.time.toString()))

            val action = UpdatePasswordFragmentDirections.actionUpdateFragmentToUserFragment(args.user)
            findNavController().navigate(action)
        }else if (newPassword != confPassword){
            Toast.makeText(requireContext(), "Passwords are not same", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Old Password is incorrect", Toast.LENGTH_SHORT).show()
        }
    }

}