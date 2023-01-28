package com.example.todoapp.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import com.example.todoapp.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.*

class BottomSheet : BottomSheetDialogFragment() {
    lateinit var date: TextView
    var timeNow: Calendar = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        initListeners()

    }

    @SuppressLint("SetTextI18n")
    fun init(view: View) {
        date = view.findViewById(R.id.date_text)
        date.text = "${timeNow.get(Calendar.DAY_OF_MONTH)} / ${timeNow.get(Calendar.MONTH) + 1} / ${
            timeNow.get(Calendar.YEAR)
        }"
    }

    fun initListeners() {
        date.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                object : OnDateSetListener {
                    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
                        date.text = "$day / ${month + 1} / $year"
                    }
                },
                timeNow.get(Calendar.YEAR),
                timeNow.get(Calendar.MONTH),
                timeNow.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }
}