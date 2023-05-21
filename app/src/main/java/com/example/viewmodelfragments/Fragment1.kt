package com.example.viewmodelfragments

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.Navigation
import android.widget.ArrayAdapter
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import androidx.navigation.Navigation.findNavController

class Fragment1 : Fragment() {
    private val viewModel: MainActivityViewModel by activityViewModels()
    private val optionsFilter = arrayOf("All Courses", "CS Only", "Math Only", "Other Only")
    private val optionsSort = arrayOf("By Course Code", "By Term", "By Mark")
    //private var sortBy = viewModel.sortBy
    //private var filterBy = viewModel.filterBy
    private lateinit var adapterSort: ArrayAdapter<String>
    private lateinit var adapterFilter: ArrayAdapter<String>
    private fun replaceFragment() {
        /*val bundle = Bundle()
        bundle.putString("sortBy", viewModel.sortBy)
        bundle.putString("filterBy", viewModel.filterBy)
        val fragment = Fragment1()
        fragment.arguments = bundle
        parentFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()*/
    }


    override fun onResume() {
        super.onResume()
        requireView().invalidate()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_1, container, false)

        val addButton = root.findViewById<Button>(R.id.addButton)

        val sort = root.findViewById<Spinner>(R.id.sort)
        val filter = root.findViewById<Spinner>(R.id.filter)




        val entryAdapter = EntryAdapter(requireContext(), viewModel)
        adapterFilter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, optionsFilter)
        adapterFilter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        filter.adapter = adapterFilter
        filter.setSelection(optionsFilter.indexOf(viewModel.filterBy))
        filter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                viewModel.filterBy = parent.getItemAtPosition(position).toString()
                replaceFragment()
                viewModel.changeFilter()
                entryAdapter.updateEntries()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }




        adapterSort = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, optionsSort)
        adapterSort.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sort.adapter = adapterSort
        sort.setSelection(optionsSort.indexOf(viewModel.sortBy))
        sort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                viewModel.sortBy = parent.getItemAtPosition(position).toString()
                viewModel.changeSort()
                entryAdapter.updateEntries()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

        addButton.setOnClickListener {
            findNavController().navigate(R.id.action_f1_f2)
        }


        val listView = root.findViewById<ListView>(R.id.entries)




        listView.adapter = entryAdapter
        listView.setOnItemClickListener { _, _, position, _ ->
            val entry = entryAdapter.getItem(position)
            viewModel.selectedEntry = entry
            findNavController().navigate(R.id.action_f1_f3)
        }





        addButton.setOnClickListener {
            findNavController().navigate(R.id.action_f1_f2)
        }

        return root

    }
}

class EntryAdapter(private val context: Context, private val viewModel: MainActivityViewModel) : BaseAdapter() {
    private var entries: List<Entry> = viewModel.filteredEntries

    override fun getCount(): Int {
        return entries.size
    }

    override fun getItem(position: Int): Entry {
        return entries[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.entry_item, parent, false)
        }

        val entry = entries[position]
        view!!.findViewById<TextView>(android.R.id.text1).text = Html.fromHtml("<b>${entry.courseName}</b>   ${if (entry.wd) "wd" else entry.mark}   ${entry.term}")
        view!!.findViewById<TextView>(android.R.id.text2).apply {
            text = "Description: ${entry.description}"
            setTypeface(typeface, Typeface.ITALIC)
        }
        view.setBackgroundColor(entry.color)

        val editButton = view.findViewById<Button>(R.id.editButton)
        editButton.setOnClickListener {
            viewModel.selectedEntry = entry
            Navigation.findNavController(view).navigate(R.id.action_f1_f3)
        }

        val deleteButton = view.findViewById<Button>(R.id.deleteButton)
        deleteButton.setOnClickListener {
            viewModel.entries.remove(entry)
            notifyDataSetChanged()
        }

        return view
    }

    fun updateEntries() {
        //"All Courses", "CS Only", "Math Only", "Other Only"
        val filteredEntries = viewModel.filteredEntries
        if (viewModel.filterBy == "CS Only") {
            filteredEntries.filter { it.courseType == "CS" }
        }
        else if (viewModel.filterBy == "Math Only") {
            filteredEntries.filter { it.courseType == "MATH" }
        }
        else if (viewModel.filterBy == "Other Only") {
            filteredEntries.filter { it.courseType == "Other" }
        }
        else {

        }
        if (viewModel.sortBy == "By Mark") {
            entries = filteredEntries.sortedByDescending { entry -> entry.mark }
        }
        else if (viewModel.sortBy == "By Course Code") {
            entries = filteredEntries.sortedBy { entry -> entry.courseName }
        }
        else if (viewModel.sortBy == "By Term") {
            entries = filteredEntries.sortedBy { entry -> entry.term }
        }

        notifyDataSetChanged()
    }
}







