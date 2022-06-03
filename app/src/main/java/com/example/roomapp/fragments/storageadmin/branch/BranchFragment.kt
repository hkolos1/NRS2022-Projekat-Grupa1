package com.example.roomapp.fragments.storageadmin.branch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomapp.R
import com.example.roomapp.viewmodel.BranchViewModel
import kotlinx.android.synthetic.main.fragment_branch.view.*

class BranchFragment : Fragment() {

    private lateinit var mBranchViewModel: BranchViewModel
    private val args by navArgs<BranchFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_branch, container, false)

        // RecyclerView
        val adapter = BranchAdapter()
        val recyclerView = view.recyclerBranches

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // BranchViewModel
        mBranchViewModel = ViewModelProvider(this).get(BranchViewModel::class.java)
        mBranchViewModel.readAllData.observe(viewLifecycleOwner, Observer{
            branch -> adapter.setData(branch,args.user)
        })

        return view
    }

}