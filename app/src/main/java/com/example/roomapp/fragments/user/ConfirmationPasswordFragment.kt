package com.example.roomapp.fragments.user

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
import com.example.roomapp.model.User
import com.example.roomapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_confirmation_password.view.*
import kotlinx.android.synthetic.main.fragment_user.view.*

class ConfirmationPasswordFragment : Fragment() {
    private val args by navArgs<ConfirmationPasswordFragmentArgs>()
    private lateinit var spinerQuestion: Spinner
    private lateinit var answer: String
    private lateinit var mUserViewModel: UserViewModel




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_confirmation_password, container, false)
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)


        spinerQuestion = view.findViewById(R.id.spinnerQuestion)

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

        ArrayAdapter.createFromResource(view.context, R.array.SpinerQuestionItems,android.R.layout.simple_spinner_dropdown_item).also {
                adap -> adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinerQuestion.adapter = adap
        }

        return view

    }

    @SuppressLint("SetTextI18n")
    private fun conPassButton(view: View) {
        if (view.answerTextField.text.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill field", Toast.LENGTH_SHORT).show()
        } else {
            val updatedUser = User(args.user.id , args.user.firstName , args.user.lastName , args.user.age ,spinerQuestion.selectedItem.toString(),answer)
            mUserViewModel.updateUser(updatedUser)
            val action = ConfirmationPasswordFragmentDirections.actionConfirmationPasswordFragmentToUserFragment(updatedUser)
            findNavController().navigate(action)

        }

    }

}