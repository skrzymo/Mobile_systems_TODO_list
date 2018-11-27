package com.example.skrzy.ps2

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.SparseBooleanArray
import android.view.Menu
import android.view.MenuItem
import android.widget.AbsListView
import android.widget.ListView
import android.widget.Toast


class DeleteActivity : AppCompatActivity() {
    val todoList: ArrayList<TodoStart> = TodoStart.getList()
    private lateinit var listView: ListView
    private val LOG_TAG = this::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)

        Log.d(LOG_TAG, "-------")
        Log.d(LOG_TAG, "onCreate")


        listView = findViewById(R.id.to_delete_list_view)
        val adapter = TodoAdapter(this, todoList)
        listView.adapter = adapter
        listView.choiceMode = ListView.CHOICE_MODE_MULTIPLE_MODAL

        listView.setMultiChoiceModeListener(object: AbsListView.MultiChoiceModeListener {

            override fun onPrepareActionMode(mode: android.view.ActionMode?, menu: Menu?): Boolean {
                return false
            }

            override fun onDestroyActionMode(mode: android.view.ActionMode?) {

            }

            override fun onCreateActionMode(mode: android.view.ActionMode?, menu: Menu?): Boolean {
                mode?.menuInflater?.inflate(R.menu.menu_delete_layout, menu)
                return true
            }

            override fun onActionItemClicked(mode: android.view.ActionMode?, item: MenuItem?): Boolean {

                Log.d(LOG_TAG, "-------")
                Log.d(LOG_TAG, "onActionItemClicked")

                when (item?.itemId) {
                    R.id.selectAll -> {
                        val checkedCount: Int = todoList.size
                        adapter.removeSelection()
                        for (i in 0 until checkedCount) {
                            listView.setItemChecked(i, true)
                        }
                        mode?.title = "$checkedCount Selected"
                        return true
                    }

                    R.id.delete -> {
                        var checkedCount = 0
                        val builder = AlertDialog.Builder(this@DeleteActivity)
                        builder.setMessage("Do you want to delete selected record(s)?")
                        builder.setNegativeButton("No") { dialog, which ->
                        }
                        builder.setPositiveButton("Yes") { dialog, which ->
                            var selected: SparseBooleanArray = adapter.getSelectedIds()
                            for (i in (selected.size() - 1) downTo 0) {
                                if (selected.valueAt(i)) {
                                    var selectedItem: TodoStart = adapter.getItem(selected.keyAt(i))
                                    adapter.remove(selectedItem)
                                    checkedCount ++
                                }
                                mode?.finish()
                                selected.clear()
                            }
                            Toast.makeText(applicationContext, "$checkedCount task(s) deleted",Toast.LENGTH_SHORT).show()
                        }
                        val alert: AlertDialog = builder.create()
                        alert.setIcon(R.drawable.question)
                        alert.setTitle("Confirmation")
                        alert.show()
                        return true
                    }

                    else -> {
                        return false
                    }
                }
            }

            override fun onItemCheckedStateChanged(mode: android.view.ActionMode?, position: Int, id: Long, checked: Boolean) {

                Log.d(LOG_TAG, "-------")
                Log.d(LOG_TAG, "onItemCheckedStateChanged")

                val checkedCount: Int = listView.checkedItemCount
                mode?.title = "$checkedCount Selected"
                adapter.toggleSelection(position)
            }

        })
    }
}
