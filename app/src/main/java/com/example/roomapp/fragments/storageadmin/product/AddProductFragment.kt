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
import com.example.roomapp.viewmodel.CategoryViewModel
import com.example.roomapp.viewmodel.LogViewModel
import com.example.roomapp.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_add_product.view.*
import java.math.RoundingMode
import java.util.*


class AddProductFragment : Fragment() {

    private  lateinit var mProductViewModel: ProductViewModel
    private lateinit var mCategoryViewModel: CategoryViewModel
    private lateinit var name:TextView
    private lateinit var quantity:TextView
    private lateinit var unit:EditText
    private lateinit var price: EditText
    private lateinit var category : Spinner
    private lateinit var mLogViewModel: LogViewModel
    private lateinit var round : Spinner
    private val args by navArgs<AddProductFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_product, container, false)

        mProductViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        mLogViewModel = ViewModelProvider(this).get(LogViewModel::class.java)
        mCategoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)

        name = view.findViewById(R.id.addPrName)
        quantity = view.findViewById(R.id.addPrAmount)
        unit = view.findViewById(R.id.addPrUnit)
        price = view.findViewById(R.id.addPrPrice)
        category = view.findViewById(R.id.spinnerCategory)
        round = view.findViewById(R.id.spinnerRound)

        val spinnerProdAdapter = ArrayAdapter<Any>(requireContext(), android.R.layout.simple_spinner_dropdown_item)
        val spinnerProdAdapter1 = ArrayAdapter<Any>(requireContext(), android.R.layout.simple_spinner_dropdown_item)
        spinnerProdAdapter1.add("Round");
        spinnerProdAdapter1.add("Decimal")
        mCategoryViewModel.readAllData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                categ -> categ.forEach {
            spinnerProdAdapter.add(it.nameCategory)
        }
        })

        category.adapter = spinnerProdAdapter
        round.adapter = spinnerProdAdapter1

        view.addButton.setOnClickListener {
            insertDataToDatabase()
        }

        return view
    }

    private fun insertDataToDatabase() {
        val cat = category.selectedItem
        var value= quantity.text.toString();
        var finalValue=value.toDouble()
        finalValue=finalValue.toBigDecimal().setScale(3, RoundingMode.UP).toDouble()
        var price2 = price.text.toString().toDouble()
        price2=price2.toBigDecimal().setScale(2, RoundingMode.UP).toDouble()
        val finalPrice=price2
        val roundF = round.selectedItem
        var roundFinal = true;
        if (roundF.equals("Round")) {
            roundFinal = false;
            finalValue=finalValue.toInt().toDouble()
        }
        val product = Product(
            0,name.text.toString(),finalValue,unit.text.toString(),null, "Unassigned",
            cat.toString(),finalPrice,roundFinal)
        mProductViewModel.addProduct(product)
        val cal: Calendar = Calendar.getInstance()
        mLogViewModel.addLog(Log(0,args.user.firstName,"Added product ${product.prodName}",cal.time.toString()))

        Toast.makeText(requireContext(), "Successfully added!", Toast.LENGTH_LONG).show()
        findNavController().navigateUp()
    }


}