package com.example.roomapp.fragments.storageadmin.branch

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomapp.R
import com.example.roomapp.model.Log
import com.example.roomapp.viewmodel.BranchViewModel
import com.example.roomapp.viewmodel.LogViewModel
import com.example.roomapp.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_all_products.view.*
import java.util.*

class AllProductsFragment: Fragment() {
    private lateinit var mProductViewModel: ProductViewModel
    private lateinit var mBranchViewModel: BranchViewModel
    private lateinit var mLogViewModel: LogViewModel
    private val args by navArgs<AllProductsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all_products, container, false)

        val adapter = AllProductsAdapter()
        val recyclerView = view.recyclerProductsInBranch

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mProductViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        mBranchViewModel = ViewModelProvider(this).get(BranchViewModel::class.java)
        mLogViewModel = ViewModelProvider(this).get(LogViewModel::class.java)

        adapter.setData(args.currentBranch.products,args.user,args.currentBranch)

        mProductViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        view.floatingActionButtonProduct.setOnClickListener{
            val action = AllProductsFragmentDirections.actionAllProductsInBranchFragmentToAddProductsToBranchFragment(args.user,args.currentBranch)
            findNavController().navigate(action)
        }
        setHasOptionsMenu(true)

        return view
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
            args.currentBranch.products.clear()
            mBranchViewModel.updateBranch(args.currentBranch)
            val cal: Calendar = Calendar.getInstance()
            mLogViewModel.addLog(Log(0,args.user.firstName,"Deleted all products",cal.time.toString()))

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