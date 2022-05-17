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

        val adapter2 = BillPdvAdapter()
        val recyclerView2 = view.pdvView

        recyclerView2.adapter = adapter2
        recyclerView2.layoutManager = LinearLayoutManager(requireContext())



        mBillViewModel = ViewModelProvider(this).get(BillViewModel::class.java)
        mProductViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        mBranchViewModel = ViewModelProvider(this).get(BranchViewModel::class.java)
        mCategoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)

        view.billID.text=args.order.id.toString()

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

      mBranchViewModel.readAllData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                branch -> branch.forEach{ branch1 ->
            if(branch1.name == args.order.branch) {
                (branch1.products).forEach {
                    args.order.products.forEach { orPro ->
                        if(orPro.prodName == it.prodName){
                            mCategoryViewModel.readAllData.observe(viewLifecycleOwner, Observer {
                                    categories -> categories.forEach {
                                if(orPro.category==it.nameCategory){
                                    var k=0;
                                    for( j in listaOfCategories){
                                        if(j==it){
                                            k=1;
                                        }
                                    }
                                    if(k==0){
                                        listaOfCategories.add(it)
                                    }
                                }
                            }
                            })
                        }
                    }
                }
            }
        }
        })







           mBillViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            adapter.setData(args.order.products,listaOfTaxes)
        })
        mBillViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            adapter2.setData(args.order.products,listaOfCategories)
        })

        view.orderTotal.text = args.order.total.toString()

        return view
    }

}