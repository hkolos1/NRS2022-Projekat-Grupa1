package com.example.roomapp.fragments.user.orders

import android.app.AlertDialog
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.math.RoundingMode
import java.net.HttpURLConnection
import java.net.URL
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
    private val cal = Calendar.getInstance()

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
        var tot= args.order.total.toBigDecimal().setScale(2, RoundingMode.HALF_EVEN).toDouble()
        view.total.text =tot.toString()
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
        if(!args.order.bill){
            view.btnShowBill.text = "Print Bill"
            setHasOptionsMenu(true)
        }

        view.btnShowBill.setOnClickListener {
            if(order.bill) {
                val action = UpdateOrderFragmentDirections.actionUpdateOrderFragmentToBillFragment(
                    order,
                    args.user
                )
                findNavController().navigate(action)
            }else{
                sendPostRequest()
            }
        }

        view.btnFinishOrder.setOnClickListener {
            insertDataToDatabase()
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        if(args.order.bill){
            setHasOptionsMenu(false)
        }
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
        mLogViewModel.addLog(Log(0,args.user.firstName,"Updated order",cal.time.toString()))

        Toast.makeText(requireContext(), "Successfully updated!", Toast.LENGTH_LONG).show()
        findNavController().navigateUp()
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

    private fun sendPostRequest() {
        if(order.productsQuantity==0 || order.total == 0.0){
            Toast.makeText(context, "Add some products first", Toast.LENGTH_LONG).show()
            return
        }
        var reqParam = "<?xml version=\"1.0\" encoding=\"utf-8\"?><RacunZahtjev  xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
                "  <VrstaZahtjeva>0</VrstaZahtjeva>\n" +
                "  <NoviObjekat>  \n" +
                "    <StavkeRacuna>     \n" +
                "      <RacunStavka>\n" +
                "        <artikal>\n" +
                "          <Sifra>1000</Sifra>\n" +
                "          <Naziv>Test artikal</Naziv>\n" +
                "          <JM>ko</JM>\n" +
                "          <Cijena>0.50</Cijena>\n" +
                "          <Stopa>E</Stopa>\n" +
                "          <Grupa>0</Grupa>\n" +
                "          <PLU>0</PLU>\n" +
                "        </artikal>\n" +
                "        <Kolicina>1</Kolicina>\n" +
                "        <Rabat>0</Rabat>\n" +
                "      </RacunStavka>\n" +
                "    </StavkeRacuna>\n" +
                "    <VrstePlacanja>\n" +
                "\t<VrstaPlacanja>\n" +
                "        <Oznaka>Gotovina</Oznaka>\n" +
                "        <Iznos>50</Iznos>\n" +
                "      </VrstaPlacanja>\n" +
                "    </VrstePlacanja>\n" +
                "  </NoviObjekat>\n" +
                "</RacunZahtjev>"

        val mURL = URL("http://10.0.2.2:5000/sfr")
        GlobalScope.launch(Dispatchers.IO) {
            try {
                with(mURL.openConnection() as HttpURLConnection) {
                    // optional default is GET
                    requestMethod = "POST"
                    val wr = OutputStreamWriter(outputStream);
                    wr.write(reqParam);
                    wr.flush();

                    BufferedReader(InputStreamReader(inputStream)).use {
                        val response = StringBuffer()

                        var inputLine = it.readLine()
                        while (inputLine != null) {
                            response.append(inputLine)
                            inputLine = it.readLine()
                        }
                        println("Response : $response")
                        //println(response.toString().substring(252,258))

                        args.order.bill = true
                        args.order.billId = response.toString().substring(252,258).toInt()
                        args.order.billDate = response.toString().substring(379,391) + " " + response.toString().substring(514,522)
                        args.order.products.forEach {
                            mainBranch.products.forEach { branchProd ->
                                if(it.prodName == branchProd.prodName)
                                    branchProd.quantity -= it.quantity
                            }
                        }
                        mBranchViewModel.updateBranch(mainBranch)
                        mOrderViewModel.updateOrder(args.order)
                        mLogViewModel.addLog(Log(0,args.user.firstName,"Issue invoice",cal.time.toString()))
                        GlobalScope.launch(Dispatchers.Main) {
                            val action = UpdateOrderFragmentDirections.actionUpdateOrderFragmentToBillFragment(order,args.user)
                            findNavController().navigate(action)
                        }
                    }
                }
            }catch (e: Exception){
                println("Error : $e")
            }
        }
    }
}