package com.example.roomapp.fragments.admin.users.update

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.text.TextUtils
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomapp.R
import com.example.roomapp.model.Log
import com.example.roomapp.model.User
import com.example.roomapp.viewmodel.BranchViewModel
import com.example.roomapp.viewmodel.LogViewModel
import com.example.roomapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import java.util.*

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var mUserViewModel: UserViewModel
    private lateinit var mLogViewModel: LogViewModel
    private lateinit var mBranchViewModel: BranchViewModel
    private lateinit var branch : Spinner
    private lateinit var spinnerProdAdapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        mLogViewModel = ViewModelProvider(this).get(LogViewModel::class.java)
        mBranchViewModel = ViewModelProvider(this).get(BranchViewModel::class.java)

        branch = view.updateBranch

        spinnerProdAdapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item)

        mBranchViewModel.readAllData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                mBranch -> mBranch.forEach {
            spinnerProdAdapter.add(it.toString())
            }
            branch.adapter = spinnerProdAdapter

            branch.setSelection(spinnerProdAdapter.getPosition(args.currentUser.branch))
        })

        view.updateAge_et.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            @SuppressLint("SetTextI18n")
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if(view.updateAge_et.selectedItem.toString() == "User")
                    branch.visibility = View.VISIBLE
                else
                    branch.visibility = View.INVISIBLE
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }


        view.updateFirstName_et.setText(args.currentUser.firstName)
        view.updateLastName_et.setText(args.currentUser.lastName)
        view.updateAge_et.setSelection(args.currentUser.age)

        view.update_btn.setOnClickListener {
            updateItem()
        }

        // Add menu
        setHasOptionsMenu(true)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.checkbox3.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked){
                view.updateLastName_et.transformationMethod = HideReturnsTransformationMethod.getInstance();
            }else{
                view.updateLastName_et.transformationMethod = PasswordTransformationMethod.getInstance();
            }
        }
    }

    private fun updateItem() {
        val firstName = updateFirstName_et.text.toString()
        val lastName = updateLastName_et.text.toString()
        val age = updateAge_et.selectedItemPosition
        var bran: String? = null
        if(age == 2)
            bran = spinnerProdAdapter.getItem(updateBranch.selectedItemPosition)

        if (inputCheck(firstName, lastName)) {
            // Create User Object
            val updatedUser = User(args.currentUser.id, firstName, lastName, age,
                args.currentUser.question,args.currentUser.answer,bran)
            // Update Current User
            mUserViewModel.updateUser(updatedUser)
            val cal: Calendar = Calendar.getInstance()
            mLogViewModel.addLog(Log(0,args.user.firstName,"Updated user ${args.currentUser.firstName}",cal.time.toString()))

            Toast.makeText(requireContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show()
            // Navigate Back
            findNavController().navigateUp()
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mUserViewModel.deleteUser(args.currentUser)
            val cal: Calendar = Calendar.getInstance()
            mLogViewModel.addLog(Log(0,args.user.firstName,"Deleted user ${args.currentUser.firstName}",cal.time.toString()))

            Toast.makeText(
                requireContext(),
                "Successfully removed: ${args.currentUser.firstName}",
                Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${args.currentUser.firstName}?")
        builder.setMessage("Are you sure you want to delete ${args.currentUser.firstName}?")
        builder.create().show()
    }
}