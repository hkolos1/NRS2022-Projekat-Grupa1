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
import com.example.roomapp.repository.UserRepository
import com.example.roomapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_forgot_password_question.view.*

class ForgotPasswordQuestionFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var username: String
    private lateinit var answer: String
    private lateinit var repository: UserRepository
    private val args by navArgs<ForgotPasswordQuestionFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_forgot_password_question, container, false)


        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        repository = mUserViewModel.repository

        view.btn_forgotPassword.setOnClickListener {
            forgotPasswordButton(view)
        }

        view.pass_username.text ="Your username: "+args.user.firstName
        view.questionTextView.text = args.user.question

        username = args.user.firstName

        view.answerForgetPassword.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                answer = s.toString()
            }
        })

        return view
    }

    private fun forgotPasswordButton(view: View) {

        if (view.answerForgetPassword.text.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter the answer", Toast.LENGTH_SHORT).show()
        } else {
                val usersNames = args.user
                    when (usersNames.answer) {
                        answer -> {
                            val action = ForgotPasswordQuestionFragmentDirections.actionForgotPasswordQuestionFragmentToForgotNewPasswordFragment(usersNames)
                            findNavController().navigate(action)
                        }
                        else -> {
                            Toast.makeText(requireContext(), "Please check your Answer", Toast.LENGTH_SHORT).show()
                        }
                    }
        }

    }

}