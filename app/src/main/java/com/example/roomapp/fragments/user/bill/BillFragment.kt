package com.example.roomapp.fragments.user.bill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomapp.R
import com.example.roomapp.model.Branch
import com.example.roomapp.model.Log
import com.example.roomapp.viewmodel.*
import kotlinx.android.synthetic.main.fragment_bill.view.*
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.math.RoundingMode
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.util.*

class BillFragment : Fragment() {

    private lateinit var mProductViewModel: ProductViewModel
    private lateinit var mBranchViewModel: BranchViewModel
    private lateinit var mCategoryViewModel: CategoryViewModel
    private lateinit var mOrderViewModel: OrderViewModel
    private lateinit var mLogViewModel: LogViewModel
    private lateinit var mainBranch: Branch
    private val args by navArgs<BillFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bill, container, false)

        mOrderViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)
        mProductViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        mBranchViewModel = ViewModelProvider(this).get(BranchViewModel::class.java)
        mCategoryViewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        mLogViewModel = ViewModelProvider(this).get(LogViewModel::class.java)

        mBranchViewModel.readAllData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                branch -> branch.forEach{ branch1 ->
                    if(branch1.name == args.order.branch) {
                        mainBranch = branch1
                    }
                }
        })

        val adapter = BillAdapter()
        val recyclerView = view.prodView

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter.setData(args.order.products)

        val adapter2 = BillPdvAdapter()
        val recyclerView2 = view.pdvView

        recyclerView2.adapter = adapter2
        recyclerView2.layoutManager = LinearLayoutManager(requireContext())

        view.billDate.text = args.order.billDate
        view.branchName.text = args.order.branch


        view.billID.text=args.order.billId.toString()

        mCategoryViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            adapter2.setData(args.order.products,it)
        })

        view.orderTotal.text = args.order.total.toBigDecimal().setScale(2, RoundingMode.HALF_EVEN).toDouble().toString()

        return view
    }

}