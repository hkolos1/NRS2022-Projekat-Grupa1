package com.example.roomapp.fragments.user.bill

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomapp.R
import com.example.roomapp.viewmodel.BillViewModel
import com.example.roomapp.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_bill.view.*

class BillFragment : Fragment() {


    private lateinit var mBillViewModel: BillViewModel
    private lateinit var mProductViewModel: ProductViewModel
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

        adapter.setData(args.currentOrder.products)

        return view
    }

}