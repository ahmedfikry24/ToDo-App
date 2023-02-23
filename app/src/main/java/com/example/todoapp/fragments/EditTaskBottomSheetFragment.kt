package com.example.todoapp.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.todoapp.R
import com.example.todoapp.database.TaskDatabase
import com.example.todoapp.database.model.Tasks
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputLayout
import java.util.*

class EditBottomSheet(val task: Tasks) : BottomSheetDialogFragment() {
    private lateinit var date: TextView
    private lateinit var title: TextInputLayout
    private lateinit var description: TextInputLayout
    private lateinit var editTaskButton: Button
    private var timeNow: Calendar = Calendar.getInstance()

    init {
        timeNow.timeInMillis = task.date
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_task_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        initListeners()

    }

    override fun onDismiss(dialog: DialogInterface) {
        DismissListener?.onDismiss()
    }

    var DismissListener: OnDismissListener? = null

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
        description = view.findViewById(R.id.des_textInput)
        title.editText?.setText(task.title, TextView.BufferType.EDITABLE)
        description.editText?.setText(task.description, TextView.BufferType.EDITABLE)
        editTaskButton = view.findViewById(R.id.set_date_button)

    }

    @SuppressLint("SetTextI18n")
    fun initListeners() {
        date.setOnClickListener {
            DatePickerDialog(
                requireContext(),
                { p0, year, month, day ->
                    date.text = "$day / ${month + 1} / $year"
                    timeNow.set(year, month, day)
                },
                timeNow.get(Calendar.YEAR),
                timeNow.get(Calendar.MONTH),
                timeNow.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
        editTaskButton.setOnClickListener {
            if (!validate()) return@setOnClickListener
            editTask()
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

    private fun validate(): Boolean {
        var isValidate = true
        if (title.editText?.text.isNullOrBlank()) {
            isValidate = false
            title.editText?.error = "required this field"
            return isValidate
        } else {
            title.editText?.error = null
        }

        if (description.editText?.text.isNullOrBlank()) {
            isValidate = false
            title.editText?.error = "required this field"
            return isValidate
        } else {
            description.editText?.error = null
        }
        return isValidate
    }

    private fun editTask() {
        timeNow.set(Calendar.HOUR, 0)
        timeNow.set(Calendar.MINUTE, 0)
        timeNow.set(Calendar.SECOND, 0)
        timeNow.set(Calendar.MILLISECOND, 0)
        task.title = title.editText?.text.toString()
        task.description = description.editText?.text.toString()
        task.date = timeNow.timeInMillis
        TaskDatabase.createDatabase(requireContext()).tasksDao().updateTask(
            task
        )
    }
}