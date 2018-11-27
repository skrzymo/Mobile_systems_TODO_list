package com.example.skrzy.ps2

import java.util.*


class TodoStart(
    val title: String,
    val url: String) {
    companion object {

        private var todoList = ArrayList<TodoStart>(Arrays.asList(TodoStart("Zrobić projekt z systemów mobilnych",""),
            TodoStart("Zrobić projekt z systemów mobilnych 2",""),TodoStart("Zrobić zakupy",""),
            TodoStart("Sprawdzić czy są nowości na kanale URBEX","https://www.youtube.com/channel/UC7XgxJhy6N6KGMFzELHtlUQ"),
            TodoStart("Zapłacić rachunki",""),TodoStart("Umówić się do lekarza","")))

        fun getList() : ArrayList<TodoStart> {
            return todoList
        }

        fun addTask(index: Int, taskTitle: String, taskUrl: String) {
            todoList.add(index, TodoStart(taskTitle, taskUrl))
        }

        fun removeTask(index : Int) {
            todoList.removeAt(index)
        }
    }
}
