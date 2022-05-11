package com.example.roomapp.fragments.storageadmin.category

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomapp.R
import com.example.roomapp.model.Log
import com.example.roomapp.viewmodel.CategoryViewModel
import com.example.roomapp.viewmodel.LogViewModel
import com.example.roomapp.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_category.*
import kotlinx.android.synthetic.main.fragment_category.view.*
import java.util.*

class CategoryFragment : Fragment() {

    private lateinit var addCatButton : Button
    //private lateinit var categorySpinner : Spinner
    private lateinit var mCategoryViewModel: CategoryViewModel
    private lateinit var mLogViewModel: LogViewModel
    private val args by navArgs<CategoryFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_category, container, false)
        val adapter = CategoryAdapter()
        val recyclerView = view.recyclerViewCat
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mCategoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        mLogViewModel = ViewModelProvider(this).get(LogViewModel::class.java)

        mCategoryViewModel.readAllData.observe(viewLifecycleOwner, Observer { category ->
            adapter.setData(category,args.user)
        })
        addCatButton = view.findViewById(R.id.addCatButton)
        //categorySpinner = view.findViewById(R.id.categorySpinner)

        addCatButton.setOnClickListener {
            addingProduct()
        }
        return view
    }

    private fun addingProduct() {
        val action = CategoryFragmentDirections.actionCategoryFragmentToAddCategoryFragment()
        findNavController().navigate(action)
    }
}