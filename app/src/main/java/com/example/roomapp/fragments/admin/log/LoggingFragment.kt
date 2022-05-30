package com.example.roomapp.fragments.admin.log

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.roomapp.R
import com.example.roomapp.viewmodel.LogViewModel
import kotlinx.android.synthetic.main.fragment_logging.view.*

class LoggingFragment : Fragment() {

    private lateinit var mLogViewModel: LogViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_logging, container, false)

        // RecyclerviewWho
        val adapter = LoggingAdapter()
        val recycler = view.recyclerviewLog
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(requireContext())

        // UserViewModel
        mLogViewModel = ViewModelProvider(this).get(LogViewModel::class.java)
        mLogViewModel.readAllData.observe(viewLifecycleOwner, Observer { log ->
            adapter.setData(log.sortedByDescending { it.id })
        })

        return view
    }

}