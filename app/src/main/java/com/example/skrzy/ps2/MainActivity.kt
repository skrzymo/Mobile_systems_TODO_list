package com.example.skrzy.ps2

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.app.Activity
import android.graphics.Color
import android.support.design.widget.Snackbar
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    val todoList = TodoStart.getList()
    private val LOG_TAG = this::class.java.simpleName


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(LOG_TAG, "-------")
        Log.d(LOG_TAG, "onCreate")

    }

    override fun onResume() {
        super.onResume()

        Log.d(LOG_TAG, "-------")
        Log.d(LOG_TAG, "onResume")

        setSupportActionBar(findViewById(R.id.my_toolbar))

        listView = findViewById(R.id.todo_list_view)

        val adapter = TodoAdapter(this, todoList)
        listView.adapter = adapter


        listView.setOnItemClickListener {_, _, position, _->
            val selectedTask = todoList[position]

            if(selectedTask.url != "") {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(selectedTask.url)
                intent.setPackage("com.google.android.youtube")
                startActivity(intent)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {

        R.id.action_add -> {

            Log.d(LOG_TAG, "-------")
            Log.d(LOG_TAG, "onOptionsItemSelected - Add Activity")

            val intent = Intent(this, AddActivity::class.java)
            startActivityForResult(intent,1)
            true
        }

        R.id.action_delete -> {
            val intent = Intent(this, DeleteActivity::class.java)
            startActivity(intent)
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?) : Boolean {
        menuInflater.inflate(R.menu.menu_layout, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 1) {
            if(resultCode == Activity.RESULT_OK) run {
                val title = data?.getStringExtra("Title")
                if (title != null) {
                    val mySnackbar = Snackbar.make(coordinatorLayout, "Task added: $title", 5000)
                    mySnackbar.setAction(R.string.undo_string, MyUndoListener())
                    mySnackbar.setActionTextColor(Color.GREEN)
                    mySnackbar.show()
                }
            }
        }
    }

    inner class MyUndoListener : View.OnClickListener {

        override fun onClick(v: View) {
            TodoStart.removeTask(todoList.size - 1)
            onResume()
            val mySnackbar = Snackbar.make(coordinatorLayout, "Undo successful", 5000)
            mySnackbar.show()
        }
    }
}
