package com.example.mytasks

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytasks.model.Task
import com.example.mytasks.view.TaskAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var taskAdapter: TaskAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var etNewTask: EditText
    private lateinit var btnAdd: Button

    // Lista mutável para armazenar as tarefas.
    private var taskList = mutableListOf<Task>()
    private var nextId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        etNewTask = findViewById(R.id.etNewTask)
        btnAdd = findViewById(R.id.btnAdd)

        // Instancia o adapter, passando as funções lambda para os eventos.
        taskAdapter = TaskAdapter(
            onTaskCheckedChanged = { task, isChecked ->
                updateTask(task, isChecked)
            },
            onTaskDeleteClicked = { task ->
                deleteTask(task)
            }
        )

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = taskAdapter
        }

        // Listener para o botão de adicionar.
        btnAdd.setOnClickListener {
            addNewTask()
        }

        // Carrega a lista inicial (opcional).
        loadInitialTasks()
    }

    private fun addNewTask() {
        val taskName = etNewTask.text.toString()
        if (taskName.isNotBlank()) {
            val newTask = Task(id = nextId++, name = taskName)
            taskList.add(newTask)
            updateAdapter()
            etNewTask.text.clear()
        }
    }

    private fun updateTask(taskToUpdate: Task, isChecked: Boolean) {
        val taskIndex = taskList.indexOfFirst { it.id == taskToUpdate.id }
        if (taskIndex != -1) {
            taskList[taskIndex] = taskList[taskIndex].copy(isCompleted = isChecked)
            updateAdapter()
        }
    }

    private fun deleteTask(taskToDelete: Task) {
        taskList.removeAll { it.id == taskToDelete.id }
        updateAdapter()
    }

    private fun updateAdapter() {
        // Envia uma cópia da lista para o adapter. O toList() é crucial!
        taskAdapter.submitList(taskList.toList())
    }

    private fun loadInitialTasks() {
        taskList.addAll(listOf(
            Task(id = nextId++, name = "Comprar placa de video rtx 5090ti"),
            Task(id = nextId++, name = "Comprar processador ryzen 9"),
            Task(id = nextId++, name = "Comprar memoria ram 32gb"),
        ))
        updateAdapter()
    }
}
