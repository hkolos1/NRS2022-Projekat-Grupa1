package com.example.roomapp.fragments.storageadmin.category

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomapp.R
import com.example.roomapp.model.Log
import com.example.roomapp.viewmodel.CategoryViewModel
import com.example.roomapp.viewmodel.LogViewModel
import kotlinx.android.synthetic.main.fragment_category.view.*
import java.util.*

class CategoryFragment : Fragment() {

    private lateinit var addCatButton : Button
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

        addCatButton.setOnClickListener {
            addingCategory()
        }
        setHasOptionsMenu(true)

        return view
    }

    private fun addingCategory() {
        val action = CategoryFragmentDirections.actionCategoryFragmentToAddCategoryFragment(args.user)
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_product_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete_prod){
            deleteAllCategories()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllCategories() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mCategoryViewModel.deleteAllCategories()
            val cal: Calendar = Calendar.getInstance()
            mLogViewModel.addLog(Log(0,args.user.firstName,"Deleted all categories",cal.time.toString()))

            Toast.makeText(
                requireContext(),
                "Successfully removed everything",
                Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure you want to delete everything?")
        builder.create().show()
    }
}