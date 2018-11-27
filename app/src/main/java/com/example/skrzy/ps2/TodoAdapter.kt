package com.example.skrzy.ps2

import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class TodoAdapter(private val context: Context,
                  private val dataSource: ArrayList<TodoStart>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var mSelectedItemsIds: SparseBooleanArray = SparseBooleanArray()

    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): TodoStart {
        return dataSource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = inflater.inflate(R.layout.list_item_todo, parent, false)

            holder = ViewHolder()
            holder.titleTextView = view.findViewById(R.id.todo_list_title) as TextView
            holder.urlTextView = view.findViewById(R.id.todo_list_url) as TextView

            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val titleTextView = holder.titleTextView
        val urlTextView = holder.urlTextView

        val todo = getItem(position)

        titleTextView.text = todo.title
        urlTextView.text = todo.url

        return view
    }

    private class ViewHolder {
        lateinit var titleTextView: TextView
        lateinit var urlTextView: TextView
    }

    fun remove(obj: TodoStart) {
        dataSource.remove(obj)
        notifyDataSetChanged()
    }

    fun toggleSelection(position: Int) {
        selectView(position, !mSelectedItemsIds.get(position))
    }

    fun removeSelection() {
        mSelectedItemsIds = SparseBooleanArray()
        notifyDataSetChanged()
    }

    fun selectView(position: Int, value: Boolean) {
        if(value) {
            mSelectedItemsIds.put(position, value)
        } else {
            mSelectedItemsIds.delete(position)
        }
        notifyDataSetChanged()
    }


    fun getSelectedIds(): SparseBooleanArray {
        return mSelectedItemsIds
    }
}