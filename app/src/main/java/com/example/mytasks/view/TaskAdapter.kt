package com.example.mytasks.view

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mytasks.R
import com.example.mytasks.model.Task

class TaskAdapter(
    private val onTaskCheckedChanged: (Task, Boolean) -> Unit,
    private val onTaskDeleteClicked: (Task) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view, onTaskCheckedChanged, onTaskDeleteClicked)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TaskViewHolder(
        itemView: View,
        private val onTaskCheckedChanged: (Task, Boolean) -> Unit,
        private val onTaskDeleteClicked: (Task) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val tvTaskName: TextView = itemView.findViewById(R.id.tvTaskName)
        private val cbCompleted: CheckBox = itemView.findViewById(R.id.cbCompleted)
        private val btnDelete: ImageButton = itemView.findViewById(R.id.btnDelete)

        fun bind(task: Task) {
            tvTaskName.text = task.name

            cbCompleted.setOnCheckedChangeListener(null)
            cbCompleted.isChecked = task.isCompleted

            updateTaskAppearance(task)

            cbCompleted.setOnCheckedChangeListener { _, isChecked ->
                onTaskCheckedChanged(task, isChecked)
            }

            btnDelete.setOnClickListener {
                onTaskDeleteClicked(task)
            }
        }

        private fun updateTaskAppearance(task: Task) {
            if (task.isCompleted) {
                tvTaskName.paintFlags = tvTaskName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                tvTaskName.paintFlags = tvTaskName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
    }
}
