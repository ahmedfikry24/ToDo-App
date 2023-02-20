package com.example.todoapp.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.HomeActivity
import com.example.todoapp.R
import com.example.todoapp.TasksListAdapter
import com.example.todoapp.database.TaskDatabase
import com.example.todoapp.database.model.Tasks
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import java.util.*

class ListTodoFragment : Fragment() {
    lateinit var tasksRecycler: RecyclerView
    lateinit var tasksAdapter: TasksListAdapter
    lateinit var calenderView: MaterialCalendarView
    var timeNow: Calendar = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        initListener()

    }


    fun init(view: View) {
        tasksRecycler = view.findViewById(R.id.tasks_main_recycler)
        calenderView = view.findViewById(R.id.calendarView)
        calenderView.selectedDate = CalendarDay.today()
        tasksAdapter = TasksListAdapter(null)
        tasksRecycler.adapter = tasksAdapter
    }

    fun initListener() {
        getTasksByDate()
        calenderView.setOnDateChangedListener { widget, date, selected ->
            if (selected) {
                timeNow.set(Calendar.YEAR, date.year)
                timeNow.set(Calendar.MONTH, date.month - 1)
                timeNow.set(Calendar.DAY_OF_MONTH, date.day)
                getTasksByDate()
            }
        }
        tasksAdapter.onSwipe = object : TasksListAdapter.OnSwipe {
            override fun onOpen(Task: Tasks) {
                AlertDialog.Builder(activity).setTitle("Alert")
                    .setMessage("Are you sure that you want delete this Task ?")
                    .setPositiveButton(
                        "ok"
                    ) { p0, p1 ->
                        deleteTask(Task)
                        p0.dismiss()
                        getTasksByDate()
                    }.show()
            }
        }

        tasksAdapter.onImageClick = object : TasksListAdapter.OnImageClick {
            override fun onImageClick(Task: Tasks) {
                updateTask(Task)
                getTasksByDate()
            }
        }
        tasksAdapter.onItemClick = object : TasksListAdapter.OnItemClick {
            override fun onItemClick(Task: Tasks) {
                val homeActivity = activity as HomeActivity

            }

        }
    }

    fun deleteTask(Task: Tasks) {
        TaskDatabase.createDatabase(requireContext()).tasksDao().deleteTask(Task)
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

    fun updateTask(Task: Tasks) {
        Log.e("update", "successfully")
        Task.isDone = true
        val tasks = TaskDatabase.createDatabase(requireContext()).tasksDao().updateTask(Task)
    }
}