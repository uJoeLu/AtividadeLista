package com.example.mytasks

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytasks.model.Task
import com.example.mytasks.view.TaskAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var taskAdapter: TaskAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)

        // 1. Instancia o adapter e define o que acontece quando uma tarefa é marcada/desmarcada.
        taskAdapter = TaskAdapter { task, isChecked ->
            updateTask(task, isChecked)
        }

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = taskAdapter
        }

        // 2. Cria e envia a lista inicial de tarefas.
        val initialTasks = listOf(

            Task(1, "Comprar pão"),
            Task(2, "Estudar Kotlin"),
            Task(3, "Fazer caminhada"),
            Task(4, "Ligar para o cliente")
        )
        taskAdapter.submitList(initialTasks)
    }


   //Atualiza o estado de uma tarefa na lista.

    private fun updateTask(taskToUpdate: Task, isChecked: Boolean) {
        // Cria uma CÓPIA da lista atual para poder modificá-la.
        val currentList = taskAdapter.currentList.toMutableList()
        // Encontra a posição do item que foi alterado.
        val taskIndex = currentList.indexOfFirst { it.id == taskToUpdate.id }

        // Se o item for encontrado, cria uma cópia dele com o estado 'isCompleted' atualizado.
        if (taskIndex != -1) {
            val updatedTask = currentList[taskIndex].copy(isCompleted = isChecked)
            currentList[taskIndex] = updatedTask

            // Envia a NOVA lista para o adapter. O DiffUtil vai detectar a mudança e animar o item.
            taskAdapter.submitList(currentList)
        }
    }
}