package com.example.viewmodelfragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController

class Fragment3 : Fragment() {
   private val viewModel: MainActivityViewModel by activityViewModels()
   override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
   ): View? {
      val view = inflater.inflate(R.layout.fragment_3, container, false)
      val title = view.findViewById<TextView>(R.id.titleTextView)
      val saveButton = view.findViewById<Button>(R.id.saveButton)
      val markEditText = view.findViewById<EditText>(R.id.markEditText)
      val termSpinner = view.findViewById<Spinner>(R.id.termEditText)
      val descriptionEditText = view.findViewById<EditText>(R.id.descriptionEditText)
      val wdSwitch = view.findViewById<Switch>(R.id.wdSwitch)
      val cancelButton = view.findViewById<Button>(R.id.cancelButton)
      var isDescriptionEdited = false
      var isMarkEdited = false
      val adapter = ArrayAdapter.createFromResource(requireContext(),
         R.array.term_options,
         android.R.layout.simple_spinner_item
      )
      title.text = viewModel.selectedEntry!!.courseName

      descriptionEditText.addTextChangedListener(object : TextWatcher {
         override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
         override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            isDescriptionEdited = true
         }
         override fun afterTextChanged(s: Editable?) {}
      })
      markEditText.addTextChangedListener(object : TextWatcher {
         override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
         override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            isMarkEdited = true
         }
         override fun afterTextChanged(s: Editable?) {}
      })

      adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
      termSpinner.adapter = adapter

      wdSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
         markEditText.isEnabled = !isChecked
      }
      var selectedTerm = viewModel.selectedEntry!!.term
      val selectedTermIndex = Terms.values().indexOf(selectedTerm)
      termSpinner.setSelection(selectedTermIndex)
      termSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
         override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            selectedTerm = Terms.values()[position]

            // Do something with the selected color

         }

         override fun onNothingSelected(parent: AdapterView<*>?) {
            // Do nothing
         }
      }
      cancelButton.setOnClickListener {
         findNavController().navigateUp()
      }


      saveButton.setOnClickListener {
         val entry = viewModel.selectedEntry

         if (entry != null) {
            if (isDescriptionEdited)entry.description = descriptionEditText.text.toString()
            if (!wdSwitch.isChecked) {
               if (isMarkEdited)entry.mark = markEditText.text.toString().toInt()
            }
            entry.wd = wdSwitch.isChecked
            entry.term = selectedTerm
            entry.findColor(entry.context)
         }

         findNavController().navigateUp()
      }
      // Inflate the layout for this fragment
      // (note fragment 3 layout includes fragment 1 and fragment 2!)
      return view
   }
}
