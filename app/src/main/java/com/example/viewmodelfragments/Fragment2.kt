package com.example.viewmodelfragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

class Fragment2 : Fragment() {

    private val viewModel: MainActivityViewModel by activityViewModels()
    private var courseCode: String = ""
    private var description: String = ""
    private var mark: Int = -1
    private var term: Terms = Terms.F19
    private var wd: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_2, container, false)


        val courseCodeEntry = root.findViewById<TextView>(R.id.editTextCourseCode)

        courseCodeEntry.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text = s.toString()
                courseCode = text
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        val descriptionEntry = root.findViewById<TextView>(R.id.editTextDescription)

        descriptionEntry.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text = s.toString()
                description = text
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        val markEntry = root.findViewById<TextView>(R.id.editMark)

        markEntry.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text = s.toString()
                mark = text.toInt()
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        val wdSwitch = root.findViewById<Switch>(R.id.wdSwitch)

        wdSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            wd = isChecked
            markEntry.isEnabled = !isChecked
        }

        val spinner: Spinner = root.findViewById(R.id.spinnerTerm)
        val adapter = ArrayAdapter.createFromResource(requireContext(),
            R.array.term_options,
            android.R.layout.simple_spinner_item
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedTerm = Terms.values()[position]
                // Do something with the selected color
                term = selectedTerm
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }

        val cancelButton = root.findViewById<Button>(R.id.cancel)
        cancelButton.setOnClickListener {
            findNavController().navigate(R.id.action_f2_f1)
        }

        val createButton = root.findViewById<Button>(R.id.create)
        createButton.setOnClickListener {
            if (courseCode.isEmpty() || (mark == -1 && wd == false)) {
                val toast = Toast.makeText(context, "please enter a course code and mark", Toast.LENGTH_SHORT)
                toast.setGravity(Gravity.TOP, 0, 0) // set gravity to top
                toast.show()
            }
            else {
                val context: Context = requireContext()
                viewModel.create(courseCode, description, mark, wd, term, context)
                findNavController().navigate(R.id.action_f2_f1)
            }
        }

        return root
    }
}