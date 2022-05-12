package com.example.roomapp.fragments.storageadmin.category

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomapp.R
import com.example.roomapp.model.Category
import com.example.roomapp.viewmodel.CategoryViewModel
import com.example.roomapp.viewmodel.LogViewModel
import kotlinx.android.synthetic.main.fragment_update_category.*
import kotlinx.android.synthetic.main.fragment_update_category.view.*
import kotlinx.android.synthetic.main.fragment_update_product.*
import kotlinx.android.synthetic.main.fragment_update_product.view.*
import java.util.*


class UpdateCategoryFragment : Fragment() {
    private val args by navArgs<UpdateCategoryFragmentArgs>()
    private lateinit var mLogViewModel: LogViewModel
    private lateinit var  mCategoryViewModel: CategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_update_category, container, false)

        mCategoryViewModel=ViewModelProvider(this).get(CategoryViewModel::class.java)
        mLogViewModel = ViewModelProvider(this).get(LogViewModel::class.java)

        view.updateCatName.setText(args.category.nameCategory)

        view.updatePdv.setText(args.category.pdv.toString())

        view.updatePdvName.setText(args.category.pdvName)

        view.updateCategoryButton.setOnClickListener {
           updateItem()
        }

        return view
    }

    private fun updateItem(){
        val catName=updateCatName.text.toString()
        //val prDelStat=args.currentProduct.deliveryStatus
        val pdv=updatePdv.text.toString().toInt()
        val pdvName=updatePdvName.text.toString()

        if(inputCheck(catName, pdvName, updatePdv.text)){
            val updatedCategory= Category(args.category.id, catName, pdvName, pdv)
            mCategoryViewModel.updateCategory(updatedCategory)
            val cal: Calendar = Calendar.getInstance()
//            mLogViewModel.addLog(Log(0,args.user.firstName,"Updated product",cal.time.toString()))

            Toast.makeText(requireContext(),"Category updated", Toast.LENGTH_LONG).show()
            findNavController().navigateUp()
        }else{
            Toast.makeText(requireContext(),"Please fill out all fields! ", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(prName: String, prDevStat: String, quan: Editable): Boolean{
        return !(TextUtils.isEmpty(prName) && TextUtils.isEmpty(prDevStat) && quan.isEmpty() )
    }

}