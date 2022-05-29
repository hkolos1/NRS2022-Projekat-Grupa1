package com.example.roomapp.fragments.storageadmin.branch.places

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomapp.R
import com.example.roomapp.model.Branch
import com.example.roomapp.model.Log
import com.example.roomapp.model.Product
import com.example.roomapp.viewmodel.BranchViewModel
import com.example.roomapp.viewmodel.LogViewModel
import com.example.roomapp.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_add_places.*
import kotlinx.android.synthetic.main.fragment_add_places.view.*
import kotlinx.android.synthetic.main.fragment_add_products.*
import kotlinx.android.synthetic.main.fragment_add_products.view.*
import java.util.*

class AddPlaceFragment: Fragment() {

    private lateinit var mProductViewModel: ProductViewModel
    private lateinit var mBranchViewModel: BranchViewModel
    private lateinit var mLogViewModel: LogViewModel
    private val args by navArgs<AddPlaceFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_places, container, false)

        mProductViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        mBranchViewModel = ViewModelProvider(this).get(BranchViewModel::class.java)
        mLogViewModel = ViewModelProvider(this).get(LogViewModel::class.java)

        view.textViewBranchName.text = "Branch "+args.branch.name

        view.btn_assign_place.setOnClickListener{
            val chosenBranch: Branch = args.branch
            val place = edit_assign_place_name.text.toString()

            args.branch.places.add(place)
            mBranchViewModel.updateBranch(args.branch)

            val cal: Calendar = Calendar.getInstance()
            mLogViewModel.addLog(Log(0,args.user.firstName,"Assigned $place to ${chosenBranch.name}",cal.time.toString()))
            Toast.makeText(requireContext(), "Assigned " + place + " to " + chosenBranch.name + " successfully!", Toast.LENGTH_SHORT).show()

            findNavController().navigateUp()
        }

        return view
    }
}