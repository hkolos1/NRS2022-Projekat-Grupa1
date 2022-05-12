package com.example.roomapp.fragments.storageadmin.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.roomapp.R
import com.example.roomapp.model.Category
import com.example.roomapp.viewmodel.CategoryViewModel
import com.example.roomapp.viewmodel.LogViewModel
import kotlinx.android.synthetic.main.fragment_add_category.view.*
import java.util.*


class AddCategoryFragment : Fragment() {

    private  lateinit var mCategoryViewModel: CategoryViewModel
    private lateinit var catName:EditText
    private lateinit var addPdv:EditText
    private lateinit var pdvInt:EditText
    private lateinit var mLogViewModel: LogViewModel
//    private val args by navArgs<AddCategoryFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_category, container, false)

        mCategoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        mLogViewModel = ViewModelProvider(this).get(LogViewModel::class.java)

        catName = view.findViewById(R.id.addCatName)
        addPdv = view.findViewById(R.id.addPdvName)
        pdvInt = view.findViewById(R.id.addPdv)

        view.addCategoryButton.setOnClickListener {
            insertDataToDatabase()
        }

        return view
    }

    private fun insertDataToDatabase() {
        val category = Category(0, catName.text.toString(),addPdv.text.toString(), pdvInt.text.toString().toInt() )
        mCategoryViewModel.addCategory(category)
        val cal: Calendar = Calendar.getInstance()
//        mLogViewModel.addLog(Log(0,args.user.firstName,"Added product",cal.time.toString()))

        Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
        findNavController().navigateUp()
    }


}