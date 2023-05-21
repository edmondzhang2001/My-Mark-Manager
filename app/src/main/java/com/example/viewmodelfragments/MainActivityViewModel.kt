package com.example.viewmodelfragments

import android.graphics.Color
import android.util.Log
import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.example.viewmodelfragments.R
import androidx.lifecycle.*


class Entry(cn: String, d: String, m: Int, w: Boolean, t: Terms, context: Context) {
    var courseName = cn
    var description = d
    var mark = m
    var wd = w
    var term = t
    var courseType = ""
    var color = 0
    val context = context

    fun findColor(context: Context) {
        if (wd) {
            color = ContextCompat.getColor(context, R.color.Teal)
        }
        else if (mark >= 0 && mark < 50) {
            color = ContextCompat.getColor(context, R.color.Coral)
        }
        else if (mark >= 50 && mark < 60) {
            color = ContextCompat.getColor(context, R.color.Blue)
        }
        else if (mark >= 60 && mark < 91) {
            color = ContextCompat.getColor(context, R.color.Green)
        }
        else if (mark >= 91 && mark < 96) {
            color = ContextCompat.getColor(context, R.color.Silver)
        }
        else if (mark >= 96 && mark <= 100) {
            color = ContextCompat.getColor(context, R.color.Gold)
        }
    }




    fun findType() {
        var str = courseName.uppercase()
        val firstSpaceIndex = str.indexOf(" ")
        var type: String = ""
        if (firstSpaceIndex != -1) {
            type = str.substring(0, firstSpaceIndex)
        }
        else {
            type = "other"
        }
        if (type == "STAT" || type == "CO") {
            type = "MATH"
        }
        println(type)
        if (type != "CS" && type != "MATH") {
            courseType = "other"
        }
        else {
            courseType = type
        }

    }


    init {
        findType()
        findColor(context)
    }

}

class MainActivityViewModel() : ViewModel() {

    val counter: MutableLiveData<Int> = MutableLiveData<Int>(0)
    val entries: ArrayList<Entry> = ArrayList()
    var filteredEntries: ArrayList<Entry> = ArrayList()
    var filterBy : String = "All Courses"
    var sortBy: String = "By Course Code"
    var selectedEntry: Entry? = null


    init {
        println("MainActivityViewModel startup")
    }

    fun changeFilter() {
        Log.d("TAG", "Message to print")
        filteredEntries.clear()
        if (filterBy == "All Courses") {
            for (item in entries) {
                filteredEntries.add(item)
            }
        }
        else if (filterBy == "CS Only") {
            for (item in entries) {
                if (item.courseType == "CS") {
                    filteredEntries.add(item)
                }
            }
        }
        else if (filterBy == "Math Only") {
            for (item in entries) {
                if (item.courseType == "MATH") {
                    filteredEntries.add(item)
                }
            }
        }
        else if (filterBy == "Other Only") {
            for (item in entries) {
                if (item.courseType == "Other") {
                    filteredEntries.add(item)
                }
            }
        }
    }

    fun changeSort() {
        if (sortBy == "By Course Code") {
            filteredEntries.sortWith(compareBy { it.courseName })
        } else if (sortBy == "By Mark") {
            filteredEntries.sortWith(Comparator { entry1, entry2 ->
                if (entry1.wd == true && entry2.wd == false) {
                    -1
                } else if (entry1.wd == false && entry2.wd == true) {
                    1
                } else if (entry1.wd == true && entry2.wd == true) {
                    1
                } else {
                    entry1.mark - entry2.mark
                }
            })
        } else if (sortBy == "By Term") {
            filteredEntries.sortWith(compareBy { it.term })
        }
    }

    fun create(courseName: String, description: String, mark: Int, wd: Boolean, term: Terms, context: Context) {
        println(courseName + " " + description + " " + mark + " " + wd + " " + term)
        var entry = Entry(courseName, description, mark, wd, term, context)
        entries.add(entry)
        changeFilter()
        for (item in filteredEntries) {
            Log.d(item.courseName, "${item.mark}")
        }

    }

    fun increment() {
        counter.value = (counter.value ?: 0) + 1
    }

    fun reset() {
        counter.value = 0
    }

}