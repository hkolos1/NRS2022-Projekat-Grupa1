package com.example.roomapp.fragments.storageadmin.branch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomapp.R
import kotlinx.android.synthetic.main.custom_row_product.view.*
import kotlinx.android.synthetic.main.fragment_branch_chooser.view.*


class BranchChooserFragment: Fragment() {
    private val args by navArgs<BranchChooserFragmentArgs>()

    private lateinit var textViewBranchChooser: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_branch_chooser, container, false)

        textViewBranchChooser = view.findViewById(R.id.textViewBranchChooser)
        textViewBranchChooser.text = args.currentBranch.name

        val currentitem = args.currentBranch

        view.btn_branch_viewProducts.setOnClickListener{
            val action = BranchChooserFragmentDirections.actionBranchChooserToAllProducts(currentitem)
            findNavController().navigate(action)
        }

        view.btn_branch_addProduct.setOnClickListener{
            val action = BranchChooserFragmentDirections.actionBranchChooserToAddProducts()
            findNavController().navigate(action)
        }

        return view
    }

}