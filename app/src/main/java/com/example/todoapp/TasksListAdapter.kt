package com.example.todoapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.todoapp.database.model.Tasks
import com.zerobranch.layout.SwipeLayout

class TasksListAdapter(var items: List<Tasks>?) : Adapter<TasksListAdapter.viewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.tast_item_recycler, parent, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.title.text = items?.get(position)?.title
        holder.description.text = items?.get(position)?.description
        holder.swipeLayout.setOnActionsListener(object : SwipeLayout.SwipeActionsListener {
            override fun onOpen(direction: Int, isContinuous: Boolean) {
                if (direction == SwipeLayout.LEFT) {
                    onSwipe?.onOpen(items?.get(position)!!)
                    holder.swipeLayout.close()
                }
            }

            override fun onClose() {}

        })
    }

    var onSwipe: OnSwipe? = null

    interface OnSwipe {
        fun onOpen(Task: Tasks)
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
        val swipeLayout: SwipeLayout = view.findViewById(R.id.swipe_layout)

    }
}