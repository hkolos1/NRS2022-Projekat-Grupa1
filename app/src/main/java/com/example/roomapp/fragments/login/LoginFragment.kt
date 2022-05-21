package com.example.roomapp.fragments.login

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.roomapp.R
import com.example.roomapp.model.Branch
import com.example.roomapp.model.Category
import com.example.roomapp.model.Log
import com.example.roomapp.model.User
import com.example.roomapp.repository.BranchRepository
import com.example.roomapp.repository.UserRepository
import com.example.roomapp.viewmodel.BranchViewModel
import com.example.roomapp.viewmodel.CategoryViewModel
import com.example.roomapp.viewmodel.LogViewModel
import com.example.roomapp.viewmodel.UserViewModel
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*

class LoginFragment : Fragment() {

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var mLogViewModel: LogViewModel
    private lateinit var mBranchViewModel: BranchViewModel
    private lateinit var mCategoryViewModel: CategoryViewModel
    private lateinit var repository: UserRepository
    private lateinit var username: String
    private lateinit var password: String
    private lateinit var layout: TextInputLayout
    private lateinit var layoutPass: TextInputLayout
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
        mBranchViewModel = ViewModelProvider(this).get(BranchViewModel::class.java)
        mCategoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)

        layout = view.findViewById(R.id.userNameTextFieldLayout)
        layoutPass = view.findViewById(R.id.passwordTextFieldLayout)

        repository = mUserViewModel.repository

        view.submitButton.setOnClickListener {
            loginButton(view)
        }

        view.forgotPassword.setOnClickListener {
            forgotTextView()
        }

        view.userNameTextField.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                username = s.toString()
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

    private fun forgotTextView() {
        findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*view.checkbox.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked){
                view.passwordTextField.transformationMethod = HideReturnsTransformationMethod.getInstance();
            }else{
                view.passwordTextField.transformationMethod = PasswordTransformationMethod.getInstance();
            }
        }*/
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        uiScope.launch {
            val usersNames = repository.getUserName("Admin")
            if (usersNames == null) {
                repository.addUser(User(0,"admin","admin",0,"","",null))
                repository.addUser(User(0,"user","user",2,"","","Sarajevo"))
                mBranchViewModel.addBranch(Branch(1,"Sarajevo", mutableListOf()))
                mBranchViewModel.addBranch(Branch(2,"Mostar", mutableListOf()))
                mBranchViewModel.addBranch(Branch(3,"Banja Luka", mutableListOf()))
                mCategoryViewModel.addCategory(Category(0,"PDV","PDV",17))
            }
        }
    }

    private fun loginButton(view: View) {
        if (view.userNameTextField.text?.isEmpty() == true || view.passwordTextField.text?.isEmpty() == true) {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            layout.isErrorEnabled = true
            layout.error = "Please fill username field"
            layoutPass.isErrorEnabled = true
            layoutPass.error = "Please fill password field"
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
                        else if(usersNames.age == 1){
                            val action = LoginFragmentDirections.actionLoginFragmentToStorageAdminFragment(usersNames)
                            findNavController().navigate(action)
                        }
                        else{
                            val action = LoginFragmentDirections.actionLoginFragmentToUserFragment(usersNames)
                            findNavController().navigate(action)
                        }
                    }else{
                        Toast.makeText(requireContext(), "Please check your Password", Toast.LENGTH_SHORT).show()
                        layoutPass.isErrorEnabled = true
                        layoutPass.error = "Please check your Password"
                    }
                } else {
                    Toast.makeText(requireContext(), "User doesnt exist!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
