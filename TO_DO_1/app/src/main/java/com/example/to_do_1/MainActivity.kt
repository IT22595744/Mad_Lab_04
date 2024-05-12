package com.example.to_do_1

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.to_do_1.Adapter.ToDoAdapter
import com.example.to_do_1.Model.ToDoModel
import com.example.to_do_1.Utils.DatabaseHandler

class MainActivity : AppCompatActivity(), DialogCloseListener {

    private lateinit var db: DatabaseHandler

    private lateinit var tasksRecyclerView: RecyclerView
    private lateinit var tasksAdapter: ToDoAdapter
    private lateinit var fab: FloatingActionButton

    private var taskList = mutableListOf<ToDoModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        db = DatabaseHandler(this)
        db.openDatabase()

        tasksRecyclerView = findViewById(R.id.tasksRecyclerView)
        tasksRecyclerView.layoutManager = LinearLayoutManager(this)
        tasksAdapter = ToDoAdapter(db, this)
        tasksRecyclerView.adapter = tasksAdapter

        val itemTouchHelper = ItemTouchHelper(RecyclerItemTouchHelper(tasksAdapter))
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView)

        fab = findViewById(R.id.fab)

        // Assuming db.allTasks returns a list of Any
        val tasks: List<Any> = db.allTasks

// Assuming ToDoModel is the correct type for tasks
        val typedTasks: List<ToDoModel> = tasks.filterIsInstance<ToDoModel>()

// Add all tasks to taskList
        taskList.addAll(typedTasks)

        taskList.reverse()

        tasksAdapter.setTasks(taskList)

        fab.setOnClickListener {
            AddNewTask.newInstance().show(supportFragmentManager, AddNewTask.TAG)
        }
    }

    override fun handleDialogClose(dialog: DialogInterface?) {
        taskList.clear()
        // Assuming db.allTasks returns a list of Any
        val tasks: List<Any> = db.allTasks

// Assuming ToDoModel is the correct type for tasks
        val typedTasks: List<ToDoModel> = tasks.filterIsInstance<ToDoModel>()

// Add all tasks to taskList
        taskList.addAll(typedTasks)

        taskList.reverse()
        tasksAdapter.setTasks(taskList)
        tasksAdapter.notifyDataSetChanged()
    }
}
