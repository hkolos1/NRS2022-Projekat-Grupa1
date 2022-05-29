package com.example.roomapp.fragments.storageadmin.branch.places

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomapp.R
import com.example.roomapp.viewmodel.BranchViewModel
import com.example.roomapp.viewmodel.LogViewModel
import com.example.roomapp.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_all_places.view.*

class AllPlacesFragment: Fragment() {
    private lateinit var mProductViewModel: ProductViewModel
    private lateinit var mBranchViewModel: BranchViewModel
    private lateinit var mLogViewModel: LogViewModel
    private val args by navArgs<AllPlacesFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all_places, container, false)

        val adapter = AllPlacesAdapter()
        val recyclerView = view.recyclerPlacesInBranch

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mProductViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        mBranchViewModel = ViewModelProvider(this).get(BranchViewModel::class.java)
        mLogViewModel = ViewModelProvider(this).get(LogViewModel::class.java)

        adapter.setData(args.currentBranch.places,args.user,args.currentBranch, mBranchViewModel)

        mProductViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        view.floatingActionButtonPlaces.setOnClickListener{
            val action = AllPlacesFragmentDirections.actionAllPlacesFragmentToAddPlaceFragment(args.user,args.currentBranch)
            findNavController().navigate(action)
        }

        return view
    }

}