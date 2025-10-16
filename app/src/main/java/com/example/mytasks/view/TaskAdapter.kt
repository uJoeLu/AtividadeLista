package com.example.mytasks.view

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mytasks.R
import com.example.mytasks.model.Task

class TaskAdapter(
    private val onTaskCheckedChanged: (Task, Boolean) -> Unit
) : ListAdapter<Task, TaskAdapter.TaskViewHolder>(TaskDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view, onTaskCheckedChanged)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class TaskViewHolder(
        itemView: View,
        private val onTaskCheckedChanged: (Task, Boolean) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val tvTaskName: TextView = itemView.findViewById(R.id.tvTaskName)
        private val cbCompleted: CheckBox = itemView.findViewById(R.id.cbCompleted)

        fun bind(task: Task) {
            tvTaskName.text = task.name

            // Remove o listener antigo para evitar que seja acionado durante a reciclagem.
            cbCompleted.setOnCheckedChangeListener(null)

            // Define o estado do CheckBox com base nos dados do item ATUAL.
            cbCompleted.isChecked = task.isCompleted

            updateTaskAppearance(task)

            // Adiciona o novo listener para responder às interações do usuário.
            cbCompleted.setOnCheckedChangeListener { _, isChecked ->
                onTaskCheckedChanged(task, isChecked)
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
