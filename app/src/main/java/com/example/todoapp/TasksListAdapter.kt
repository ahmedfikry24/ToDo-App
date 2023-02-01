package com.example.todoapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.todoapp.database.model.Tasks

class TasksListAdapter(var items: List<Tasks>?) : Adapter<TasksListAdapter.viewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.tast_item_recycler, parent, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.title.text = items?.get(position)?.title
        holder.description.text = items?.get(position)?.description
    }

    override fun getItemCount(): Int = items?.size ?: 0

    @SuppressLint("NotifyDataSetChanged")
    fun changeItems(tasks: List<Tasks>) {
        items = tasks
        notifyDataSetChanged()
    }

    class viewHolder(view: View) : ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.task_title)
        val description: TextView = view.findViewById(R.id.task_des)

    }
}