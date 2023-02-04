package com.example.todoapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.TasksListAdapter
import com.example.todoapp.database.TaskDatabase
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.util.*

class ListTodoFragment : Fragment() {
    lateinit var tasksRecycler: RecyclerView
    lateinit var tasksAdapter: TasksListAdapter
    lateinit var calenderView: MaterialCalendarView
    var timeNow: Calendar = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        initListener()
    }

    override fun onResume() {
        super.onResume()
        getTasksByDate()
    }

    fun init(view: View) {
        tasksRecycler = view.findViewById(R.id.tasks_main_recycler)
        calenderView = view.findViewById(R.id.calendarView)
        calenderView.selectedDate = CalendarDay.today()
        tasksAdapter = TasksListAdapter(null)
        tasksRecycler.adapter = tasksAdapter
    }

    fun initListener() {
        calenderView.setOnDateChangedListener { widget, date, selected ->
            if (selected) {
                timeNow.set(Calendar.YEAR, date.year)
                timeNow.set(Calendar.MONTH, date.month - 1)
                timeNow.set(Calendar.DAY_OF_MONTH, date.day)
                getTasksByDate()
            }
        }
    }

    fun getTasksByDate() {
        timeNow.set(Calendar.HOUR, 0)
        timeNow.set(Calendar.MINUTE, 0)
        timeNow.set(Calendar.SECOND, 0)
        timeNow.set(Calendar.MILLISECOND, 0)
        if (!isVisible) {
            return
        }
        val tasks = TaskDatabase.createDatabase(requireActivity()).tasksDao()
            .getTasksByDate(timeNow.timeInMillis)
        tasksAdapter.changeItems(tasks)
    }
}