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

class ListTodoFragment : Fragment() {
    lateinit var tasksRecycler: RecyclerView
    lateinit var tasksAdapter: TasksListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tasksRecycler = view.findViewById(R.id.tasks_main_recycler)
        tasksAdapter = TasksListAdapter(null)
        tasksRecycler.adapter = tasksAdapter
        getTasks()
    }

    fun getTasks() {
        if (!isVisible) {
            return
        }
        val tasks = TaskDatabase.createDatabase(requireActivity()).tasksDao().getTasks()
        tasksAdapter.changeItems(tasks)
    }
}