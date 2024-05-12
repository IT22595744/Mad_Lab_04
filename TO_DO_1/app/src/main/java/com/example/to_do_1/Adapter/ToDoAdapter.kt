package com.example.to_do_1.Adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do_1.AddNewTask
import com.example.to_do_1.MainActivity
import com.example.to_do_1.Model.ToDoModel
import com.example.to_do_1.R
import com.example.to_do_1.Utils.DatabaseHandler



class ToDoAdapter(db: DatabaseHandler, activity: MainActivity) :
    RecyclerView.Adapter<ToDoAdapter.ViewHolder>() {
    private var todoList: MutableList<ToDoModel>? = null
    private val db: DatabaseHandler
    private val activity: MainActivity

    init {
        this.db = db
        this.activity = activity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        db.openDatabase()
        val item: ToDoModel = todoList!![position]
        holder.task.setText(item.getTask())
        holder.task.setChecked(toBoolean(item.getStatus()))
        holder.task.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                db.updateStatus(item.getId(), 1)
            } else {
                db.updateStatus(item.getId(), 0)
            }
        }
    }

    private fun toBoolean(n: Int): Boolean {
        return n != 0
    }

    override fun getItemCount(): Int {
        return todoList!!.size
    }

    fun  getContext(): MainActivity {
        return activity
    }
    fun setTasks(todoList: MutableList<ToDoModel>?) {
        this.todoList = todoList
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        val item: ToDoModel = todoList!![position]
        db.deleteTask(item.getId())
        todoList!!.removeAt(position)
        notifyItemRemoved(position)
    }

    fun changeItem(position: Int, task:String){
        val item: ToDoModel = todoList!![position]
        item.setTask(task);
        notifyDataSetChanged();
    }
    fun editItem(position: Int) {
        val item: ToDoModel = todoList!![position]
        val bundle = Bundle()
        bundle.putInt("id", item.getId())
        bundle.putString("task", item.getTask())
        val fragment = AddNewTask()
        fragment.setArguments(bundle)
        fragment.show(activity.getSupportFragmentManager(), AddNewTask.TAG)
    }

    class ViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        var task: CheckBox

        init {
            task = view.findViewById<CheckBox>(R.id.todoCheckBox)
        }
    }
}