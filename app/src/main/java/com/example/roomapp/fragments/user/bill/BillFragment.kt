package com.example.roomapp.fragments.user.bill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomapp.R
import com.example.roomapp.model.Category
import com.example.roomapp.model.Product
import com.example.roomapp.viewmodel.BillViewModel
import com.example.roomapp.viewmodel.BranchViewModel
import com.example.roomapp.viewmodel.CategoryViewModel
import com.example.roomapp.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_bill.view.*

class BillFragment : Fragment() {


    private lateinit var mBillViewModel: BillViewModel
    private lateinit var mProductViewModel: ProductViewModel
    private lateinit var mBranchViewModel: BranchViewModel
    private lateinit var mCategoryViewModel: CategoryViewModel
    private var lista: MutableList<Product> = mutableListOf()
    private var listaOfCategories: MutableList<Category> = mutableListOf()
    private var listaOfTaxes: MutableList<Int> = mutableListOf()
    private val args by navArgs<BillFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bill, container, false)

        val adapter = BillAdapter()
        val recyclerView = view.prodView

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mBillViewModel = ViewModelProvider(this).get(BillViewModel::class.java)
        mProductViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        mBranchViewModel = ViewModelProvider(this).get(BranchViewModel::class.java)
        mCategoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        mBranchViewModel.readAllData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                branch -> branch.forEach{ branch1 ->
            if(branch1.name == args.order.branch) {
                (branch1.products).forEach {
                    args.order.products.forEach { orPro ->
                        if(orPro.prodName == it.prodName){
                            lista.add(it)
                        }
                    }
                }
            }
        }
        })
        for (p in args.order.products){
           mProductViewModel.readAllData.observe(viewLifecycleOwner, Observer {
               products -> products.forEach {
                   if (p.prodName == it.prodName) {
                       val a = it.category
                      mCategoryViewModel.readAllData.observe(viewLifecycleOwner, Observer {
                          categories -> categories.forEach {
                              if (a == it.nameCategory){
                                  listaOfTaxes.add(it.pdv)
                              }
                      }
                      })
                   }
           }
           })
        }
        mBillViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            adapter.setData(args.order.products,listaOfTaxes)
        })

        return view
    }

}