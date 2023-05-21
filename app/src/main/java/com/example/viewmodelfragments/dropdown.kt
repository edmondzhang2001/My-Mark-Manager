package com.example.viewmodelfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.activityViewModels


class dropdown : Fragment() {
    private val optionsFilter = arrayOf("All Courses", "CS Only", "Math Only", "Other Only")
    private val optionsSort = arrayOf("By Course Code", "By Term", "By Mark")
    private var sortBy = optionsSort[0]
    private var filterBy = optionsFilter[0]
    private lateinit var adapterSort: ArrayAdapter<String>
    private lateinit var adapterFilter: ArrayAdapter<String>

    private val viewModel: MainActivityViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_dropdown, container, false)
        val sort = root.findViewById<Spinner>(R.id.sort)
        val filter = root.findViewById<Spinner>(R.id.filter)
        adapterFilter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, optionsFilter)
        adapterFilter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        filter.adapter = adapterFilter
        filter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                viewModel.filterBy = parent.getItemAtPosition(position).toString()
                //viewModel.changeFilter()

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }




        adapterSort = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, optionsSort)
        adapterSort.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sort.adapter = adapterSort
        sort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                viewModel.sortBy = parent.getItemAtPosition(position).toString()
                //viewModel.changeSort()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }
        return root
    }


}