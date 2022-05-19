package com.example.roomapp.fragments.user.orders

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.View.INVISIBLE
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomapp.R
import com.example.roomapp.model.Branch
import com.example.roomapp.model.Log
import com.example.roomapp.model.Order
import com.example.roomapp.model.Product
import com.example.roomapp.viewmodel.BranchViewModel
import com.example.roomapp.viewmodel.LogViewModel
import com.example.roomapp.viewmodel.OrderViewModel
import com.example.roomapp.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_order_update.view.*
import java.util.*


class UpdateOrderFragment : Fragment() {

    private lateinit var mBranchViewModel: BranchViewModel
    private lateinit var mProductViewModel: ProductViewModel
    private lateinit var mOrderViewModel: OrderViewModel
    private lateinit var mLogViewModel: LogViewModel
    private lateinit var order: Order
    private lateinit var mainBranch: Branch
    private var lista: MutableList<Product> = mutableListOf()
    private val args by navArgs<UpdateOrderFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_order_update, container, false)

        mBranchViewModel = ViewModelProvider(this).get(BranchViewModel::class.java)
        mProductViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)
        mLogViewModel = ViewModelProvider(this).get(LogViewModel::class.java)
        mOrderViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)

        val adapter = UpdateOrderAdapter()
        val recyclerView = view.listOrder
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        view.total.text = args.order.total.toString()
        view.textViewUser2.text = "Products of ${args.order.name}"
        order = args.order
        if(order.bill){
            view.btn_add_order2.visibility = INVISIBLE
            view.btnFinishOrder.visibility = INVISIBLE
        }

        mBranchViewModel.readAllData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                branch -> branch.forEach{ branch1 ->
            if(branch1.name == args.order.branch) {
                    mainBranch = branch1
                (branch1.products).forEach {
                    args.order.products.forEach { orPro ->
                        if(orPro.prodName == it.prodName){
                            //it.quantity-=orPro.quantity
                            lista.add(it)
                        }
                    }
                }
            }
        }
        })

        mOrderViewModel.readAllData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            adapter.setData(order.products, lista, order, view.total)
        })

        view.btn_add_order2.setOnClickListener {
            val action = UpdateOrderFragmentDirections.actionUpdateOrderFragmentToAddProductToOrderFragment(args.user,order)
            findNavController().navigate(action)
        }

        view.btnShowBill.setOnClickListener {
            val action = UpdateOrderFragmentDirections.actionUpdateOrderFragmentToBillFragment(order,args.user)
            findNavController().navigate(action)
        }

        view.btnFinishOrder.setOnClickListener {
            insertDataToDatabase()
        }
        setHasOptionsMenu(true)

        return view
    }

    private fun insertDataToDatabase() {
        order.products.forEach {
            mainBranch.products.forEach { branchProd ->
                if(it.prodName == branchProd.prodName)
                    branchProd.quantity -= it.quantity
            }
        }
        mBranchViewModel.updateBranch(mainBranch)
        mOrderViewModel.updateOrder(order)
        val cal: Calendar = Calendar.getInstance()
        mLogViewModel.addLog(Log(0,args.user.firstName,"Updated order",cal.time.toString()))

        Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_LONG).show()
        val action = UpdateOrderFragmentDirections.actionUpdateOrderFragmentToOrderFragment(args.user)
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteOrder()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteOrder() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mOrderViewModel.deleteOrder(args.order)
            val cal: Calendar = Calendar.getInstance()
            mLogViewModel.addLog(Log(0,args.user.firstName,"Deleted order ${args.order.name}",cal.time.toString()))

            Toast.makeText(
                requireContext(),
                "Successfully removed: ${args.order.name}",
                Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${args.order.name}?")
        builder.setMessage("Are you sure you want to delete ${args.order.name}?")
        builder.create().show()
    }
}