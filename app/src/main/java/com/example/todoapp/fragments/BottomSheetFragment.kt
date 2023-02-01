package com.example.todoapp.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import com.example.todoapp.R
import com.example.todoapp.database.TaskDatabase
import com.example.todoapp.database.model.Tasks
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout
import java.util.*

class BottomSheet : BottomSheetDialogFragment() {
    lateinit var date: TextView
    lateinit var title: TextInputLayout
    lateinit var descreption: TextInputLayout
    lateinit var addTaskButton: Button
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

    var DismissListener: OnDismissListener? = null
    override fun onDismiss(dialog: DialogInterface) {
        DismissListener?.onDismiss()
    }

    interface OnDismissListener {
        fun onDismiss()
    }

    @SuppressLint("SetTextI18n")
    fun init(view: View) {
        date = view.findViewById(R.id.date_text)
        date.text = "${timeNow.get(Calendar.DAY_OF_MONTH)} / ${timeNow.get(Calendar.MONTH) + 1} / ${
            timeNow.get(Calendar.YEAR)
        }"
        title = view.findViewById(R.id.title_textInput)
        descreption = view.findViewById(R.id.des_textInput)
        addTaskButton = view.findViewById(R.id.set_date_button)

    }

    fun initListeners() {
        date.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                object : OnDateSetListener {
                    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
                        date.text = "$day / ${month + 1} / $year"
                        timeNow.set(year, month, day)
                    }
                },
                timeNow.get(Calendar.YEAR),
                timeNow.get(Calendar.MONTH),
                timeNow.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        addTaskButton.setOnClickListener {
            if (!validate()) return@setOnClickListener
            addTask()
            AlertDialog.Builder(activity).setMessage("task added successfully")
                .setIcon(R.drawable.icon_check)
                .setPositiveButton(
                    R.string.ok
                ) { p0, p1 ->
                    p0.dismiss()
                    dismiss()
                }.show().setCancelable(false)
        }
    }

    fun validate(): Boolean {
        var isValidate: Boolean = true
        if (title.editText?.text.isNullOrBlank()) {
            isValidate = false
            title.editText?.error = "required this field"
            return isValidate
        } else {
            title.editText?.error = null
        }

        if (descreption.editText?.text.isNullOrBlank()) {
            isValidate = false
            title.editText?.error = "required this field"
            return isValidate
        } else {
            descreption.editText?.error = null
        }
        return isValidate
    }

    fun addTask() {
        TaskDatabase.createDatabase(requireContext()).tasksDao().addTask(
            Tasks(
                title = title.editText?.text.toString(),
                description = descreption.editText?.text.toString(),
                date = timeNow.timeInMillis,
            )
        )
    }
}