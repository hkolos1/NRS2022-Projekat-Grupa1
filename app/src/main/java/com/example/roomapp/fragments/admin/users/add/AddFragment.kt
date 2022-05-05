package com.example.roomapp.fragments.admin.users.add

import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.roomapp.R
import com.example.roomapp.model.Log
import com.example.roomapp.model.User
import com.example.roomapp.viewmodel.LogViewModel
import com.example.roomapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import java.util.*

class AddFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var mLogViewModel: LogViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.add_btn.setOnClickListener {
            insertDataToDatabase()
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.checkbox2.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked){
                view.addLastName_et.transformationMethod = HideReturnsTransformationMethod.getInstance();
                view.addLastName_et2.transformationMethod = HideReturnsTransformationMethod.getInstance();
            }else{
                view.addLastName_et.transformationMethod = PasswordTransformationMethod.getInstance();
                view.addLastName_et2.transformationMethod = PasswordTransformationMethod.getInstance();
            }
        }
    }

    private fun insertDataToDatabase() {
        val firstName = addFirstName_et.text.toString()
        val lastName = addLastName_et.text.toString()
        val lastName2 = addLastName_et2.text.toString()
        val age = addAge_et.selectedItemPosition

        if(inputCheck(firstName, lastName, lastName2)){
            if(lastName==lastName2) {
                // Create User Object
                val user = User(
                    0,
                    firstName,
                    lastName,
                    age,
                    "",
                    ""
                )
                // Add Data to Database
                mUserViewModel.addUser(user)
                Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
                // Navigate Back
                findNavController().navigate(R.id.action_addFragment_to_listFragment)
            }else{
                Toast.makeText(requireContext(), "Password do not match", Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String, lastName2: String): Boolean{
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) &&
                TextUtils.isEmpty(lastName2))
    }

}