package com.example.roomapp.fragments.user.orders

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomapp.R
import com.example.roomapp.model.Log
import com.example.roomapp.viewmodel.LogViewModel
import com.example.roomapp.viewmodel.OrderViewModel
import com.example.roomapp.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_order.view.*
import kotlinx.android.synthetic.main.fragment_product.view.*
import java.util.*

class OrderFragment : Fragment() {

    private lateinit var addButton : Button
    private lateinit var mOrderViewModel: OrderViewModel
    private lateinit var mLogViewModel: LogViewModel
    private val args by navArgs<OrderFragmentArgs>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_order, container, false)

        val adapter = OrderAdapter()
        val recyclerView = view.recyclerViewOrder

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mOrderViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)
        mLogViewModel = ViewModelProvider(this).get(LogViewModel::class.java)

        mOrderViewModel.readAllData.observe(viewLifecycleOwner, Observer {order ->
            adapter.setData(order,args.user)
        })

        addButton = view.findViewById(R.id.addOrderButton)

        addButton.setOnClickListener {
            addingOrder()
        }
        setHasOptionsMenu(true)
        return view
    }

    private fun addingOrder() {
        val action = OrderFragmentDirections.actionOrderFragmentToAddOrderFragment(args.user)
        findNavController().navigate(action)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_product_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.menu_delete_prod){
            deleteAllOrders()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllOrders() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mOrderViewModel.deleteAllOrders()
            val cal: Calendar = Calendar.getInstance()
            mLogViewModel.addLog(Log(0,args.user.firstName,"Deleted all orders",cal.time.toString()))

            Toast.makeText(
                requireContext(),
                "Successfully removed everything",
                Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure you want to delete everything?")
        builder.create().show()
    }

}