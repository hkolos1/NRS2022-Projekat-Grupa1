package com.example.roomapp.fragments.user

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
import com.example.roomapp.model.User
import com.example.roomapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_forgot_new_password.view.*
import kotlinx.android.synthetic.main.fragment_update_password.view.*

class ForgotNewPasswordFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var newPasswordForgot: String
    private lateinit var confPasswordForgot: String
    private val args by navArgs<ForgotNewPasswordFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_forgot_new_password, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.newPasswordForgot.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                newPasswordForgot = s.toString()
            }
        })

        view.confirmPasswordForgot.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                confPasswordForgot = s.toString()
            }
        })

        view.btn_chang_pass_forgot.setOnClickListener {
            changPasswordForgotButton(view)
        }

        return view
    }

    private fun changPasswordForgotButton(view: View) {
        if (view.newPasswordForgot.text.isEmpty() || view.confirmPasswordForgot.text.isEmpty() ) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
        }else if (newPasswordForgot == confPasswordForgot){
            var updatedUser = User(args.user.id , args.user.firstName , newPasswordForgot , args.user.age ,args.user.question,args.user.answer)
            mUserViewModel.updateUser(updatedUser)

            val action = ForgotNewPasswordFragmentDirections.actionForgotNewPasswordFragmentToLoggingFragment()
            findNavController().navigate(action)
        }else{
            Toast.makeText(requireContext(), "Passwords are not same", Toast.LENGTH_SHORT).show()
        }

    }

}