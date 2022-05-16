package com.example.roomapp.fragments.user.password

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomapp.R
import com.example.roomapp.model.Log
import com.example.roomapp.model.User
import com.example.roomapp.viewmodel.LogViewModel
import com.example.roomapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_confirmation_password.view.*
import java.util.*

class ConfirmationPasswordFragment : Fragment() {
    private val args by navArgs<ConfirmationPasswordFragmentArgs>()
    private lateinit var question: EditText
    private lateinit var answer: String
    private lateinit var mUserViewModel: UserViewModel
    private lateinit var mLogViewModel: LogViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_confirmation_password, container, false)
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        mLogViewModel = ViewModelProvider(this).get(LogViewModel::class.java)

        question = view.findViewById(R.id.questionEditView)

        view.btn_con_pass.setOnClickListener {
            conPassButton(view)
        }

        view.answerTextField.addTextChangedListener(object : TextWatcher {

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

    @SuppressLint("SetTextI18n")
    private fun conPassButton(view: View) {
        if (view.answerTextField.text!!.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill field", Toast.LENGTH_SHORT).show()
        } else {
            val updatedUser = User(args.user.id , args.user.firstName , args.user.lastName , args.user.age ,
                question.text.toString(),answer)
            mUserViewModel.updateUser(updatedUser)
            val cal: Calendar = Calendar.getInstance()
            mLogViewModel.addLog(Log(0,args.user.firstName,"Added security question",cal.time.toString()))

            val action = ConfirmationPasswordFragmentDirections.actionConfirmationPasswordFragmentToUserFragment(updatedUser)
            findNavController().navigate(action)

        }

    }

}