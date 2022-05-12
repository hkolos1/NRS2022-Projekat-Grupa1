package com.example.roomapp.fragments.storageadmin.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomapp.R
import com.example.roomapp.model.Log
import com.example.roomapp.model.Product
import com.example.roomapp.viewmodel.LogViewModel
import com.example.roomapp.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_add_product.view.*
import java.util.*


class AddProductFragment : Fragment() {

    private  lateinit var mProductViewModel: ProductViewModel
    private lateinit var name:TextView
    private lateinit var quantity:TextView
    private lateinit var unit:EditText
    private lateinit var category : Spinner
    private lateinit var mLogViewModel: LogViewModel
    private val args by navArgs<AddProductFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_product, container, false)

        mProductViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        mLogViewModel = ViewModelProvider(this).get(LogViewModel::class.java)

        name = view.findViewById(R.id.addPrName)
        quantity = view.findViewById(R.id.addPrAmount)
        unit = view.findViewById(R.id.addPrUnit)

        view.addButton.setOnClickListener {
            insertDataToDatabase()
        }

        return view
    }

    private fun insertDataToDatabase() {
        val value= quantity.text.toString();
        val finalValue=Integer.parseInt(value)
        val product = Product(
            0,name.text.toString(),finalValue,unit.text.toString(),null, "Unassigned", null)
        mProductViewModel.addProduct(product)
        val cal: Calendar = Calendar.getInstance()
        mLogViewModel.addLog(Log(0,args.user.firstName,"Added product",cal.time.toString()))

        Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
        findNavController().navigateUp()
    }


}