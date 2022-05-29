package com.example.roomapp.fragments.storageadmin.delivery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomapp.R
import com.example.roomapp.fragments.storageadmin.StorageAdminFragmentDirections
import com.example.roomapp.model.Branch
import com.example.roomapp.model.Log
import com.example.roomapp.model.Product
import com.example.roomapp.model.User
import com.example.roomapp.viewmodel.BranchViewModel
import com.example.roomapp.viewmodel.LogViewModel
import com.example.roomapp.viewmodel.ProductViewModel
import com.example.roomapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_delivery.*
import kotlinx.android.synthetic.main.fragment_delivery_status.*
import kotlinx.android.synthetic.main.fragment_delivery_status.view.*
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import java.util.*

class DeliveryStatusFragment: Fragment() {
    private val args by navArgs<DeliveryStatusFragmentArgs>()

    private lateinit var textViewProd: TextView
    private lateinit var textViewStatus: TextView
    private lateinit var textViewBranch: TextView
    private lateinit var spinnerStatus: Spinner

    private lateinit var branch: Branch
    private lateinit var product: Product

    private lateinit var mProductViewModel: ProductViewModel
    private lateinit var mBranchViewModel: BranchViewModel
    private lateinit var mLogViewModel: LogViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_delivery_status, container, false)

        mProductViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        mLogViewModel = ViewModelProvider(this).get(LogViewModel::class.java)
        mBranchViewModel = ViewModelProvider(this).get(BranchViewModel::class.java)

        textViewProd = view.findViewById(R.id.delivery_status_name)
        textViewProd.text = args.currentProduct.prodName

        textViewStatus = view.findViewById(R.id.delivery_status_status)
        textViewStatus.text = args.currentProduct.deliveryStatus

        textViewBranch = view.delivery_branch_name
        textViewBranch.text = args.branch

        val spinnerList = listOf("Sent", "Delivered", "Received")

        mBranchViewModel.readAllData.observe(viewLifecycleOwner, androidx.lifecycle.Observer { it ->
            it.forEach {
                if(it.name == args.branch) {
                    branch = it
                    product = branch.products[branch.products.indexOf(args.currentProduct)]
                }
            }
        })

        spinnerStatus = view.findViewById(R.id.delivery_status_spinner)
        spinnerStatus.adapter = ArrayAdapter<String>(inflater.context, android.R.layout.simple_spinner_dropdown_item, spinnerList)

        spinnerStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        view.delivery_status_update_btn.setOnClickListener {
            updateItem()
        }

        return view
    }

    private fun updateItem() {
        val status = delivery_status_spinner.selectedItem.toString()

        // Create
        product.deliveryStatus = status
        // Update
        mBranchViewModel.updateBranch(branch)
        val cal: Calendar = Calendar.getInstance()
        mLogViewModel.addLog(Log(0,args.user.firstName,"In $branch updated ${args.currentProduct.prodName} status to $status",cal.time.toString()))

        Toast.makeText(requireContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show()
        // Navigate Back
        findNavController().navigateUp()
        findNavController().navigateUp()
        val action = StorageAdminFragmentDirections.actionStorageAdminFragmentToDeliveryFragment(args.user)
        findNavController().navigate(action)
    }
}