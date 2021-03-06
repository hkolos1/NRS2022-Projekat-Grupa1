package com.example.roomapp.fragments.admin.users.add

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomapp.R
import com.example.roomapp.fragments.admin.users.update.UpdateFragmentArgs
import com.example.roomapp.model.Log
import com.example.roomapp.model.User
import com.example.roomapp.viewmodel.BranchViewModel
import com.example.roomapp.viewmodel.LogViewModel
import com.example.roomapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_order_add_product.view.*
import kotlinx.android.synthetic.main.fragment_update.*
import java.util.*

class AddFragment : Fragment() {

    private val args by navArgs<AddFragmentArgs>()

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var mLogViewModel: LogViewModel
    private lateinit var mBranchViewModel: BranchViewModel
    private lateinit var branch : Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        mLogViewModel = ViewModelProvider(this).get(LogViewModel::class.java)
        mBranchViewModel = ViewModelProvider(this).get(BranchViewModel::class.java)

        branch = view.addBranch

        val spinnerProdAdapter = ArrayAdapter<Any>(requireContext(), android.R.layout.simple_spinner_dropdown_item)

        mBranchViewModel.readAllData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                branch -> branch.forEach {
            spinnerProdAdapter.add(it)
        }
        })

        view.addAge_et.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            @SuppressLint("SetTextI18n")
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(view.addAge_et.selectedItem.toString() == "User")
                    branch.visibility = VISIBLE
                else
                    branch.visibility = INVISIBLE
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        branch.adapter = spinnerProdAdapter

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
        var bran:String? = null
        if(age == 2)
            bran = addBranch.selectedItem.toString()

        if(inputCheck(firstName, lastName, lastName2)){
            if(lastName==lastName2) {
                // Create User Object
                val user = User(
                    0,
                    firstName,
                    lastName,
                    age,
                    "",
                    "",
                    bran
                )
                // Add Data to Database
                mUserViewModel.addUser(user)
                val cal: Calendar = Calendar.getInstance()
                mLogViewModel.addLog(Log(0,args.user.firstName,"Added user $firstName",cal.time.toString()))

                Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
                // Navigate Back
                findNavController().navigateUp()
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