package com.example.skrzy.ps2

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast

class AddActivity : AppCompatActivity() {

    private var mNewTaskTitle : EditText? = null
    private var mNewTaskUrl : EditText? = null
    private val LOG_TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        Log.d(LOG_TAG, "-------")
        Log.d(LOG_TAG, "onCreate")

        mNewTaskTitle = findViewById(R.id.new_task)
        mNewTaskUrl = findViewById(R.id.new_task_url)
    }

    fun launchMainActivity(view: View) {
        val todoListSize = TodoStart.getList().size
        if (mNewTaskTitle?.text.toString().trim().isEmpty())
        {
            Toast.makeText(applicationContext, "Task title cannot be empty!", Toast.LENGTH_SHORT).show()
        } else {
            TodoStart.addTask(todoListSize, mNewTaskTitle?.text.toString(), mNewTaskUrl?.text.toString())
            val returnIntent = Intent(this, MainActivity::class.java)
            returnIntent.putExtra("Title", mNewTaskTitle?.text.toString())
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }
}
