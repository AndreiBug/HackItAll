package com.github.compilebulai.hack.toolWindow

import com.github.compilebulai.hack.state.ToDoListService
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.content.ContentFactory
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import javax.swing.*
import java.awt.Component
import com.github.compilebulai.hack.state.Task
import java.awt.Component.LEFT_ALIGNMENT


class ToDoList : ToolWindowFactory, DumbAware {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val todoService = project.getService(ToDoListService::class.java)

        val mainPanel = JPanel(BorderLayout())
        val tasksPanel = JPanel().apply {
            layout = BoxLayout(this, BoxLayout.Y_AXIS)
            alignmentX = LEFT_ALIGNMENT
        }

        val scrollContainer = JPanel(BorderLayout()).apply {
            add(tasksPanel, BorderLayout.NORTH) // așează taskurile sus
        }

        val scrollPane = JBScrollPane(scrollContainer).apply {
            verticalScrollBarPolicy = ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS
            preferredSize = Dimension(300, 250)
        }

        UIManager.put("ProgressBar.selectionForeground", Color.YELLOW)
        UIManager.put("ProgressBar.selectionBackground", Color.WHITE)
        // Progress bar
        val progressBar = JProgressBar(0, 100).apply {
            isStringPainted = true
            isVisible = false
            foreground = Color(144, 238, 144)
        }

        // Buton de ștergere
        val removeButton = JButton("Șterge task-uri bifate")

        fun updateProgressBar() {
            val totalTasks = tasksPanel.components.count { it is TaskWithNotePanel }
            val completedTasks = tasksPanel.components.count {
                it is TaskWithNotePanel && it.checkBox.isSelected
            }
            val shouldShow = (totalTasks > 0)
            progressBar.isVisible = shouldShow
            removeButton.isVisible = shouldShow

            if (totalTasks > 0) {
                val progress = (completedTasks.toDouble() / totalTasks * 100).toInt()
                progressBar.value = progress
            }
        }

        // Funcție de adăugare nou task în UI + service
        fun addNewTask(text: String) {
            val newTask = Task(text = text, completed = false, note = "")
            todoService.addTask(newTask)

            val taskPanel = TaskWithNotePanel(project, newTask).apply {
                checkBox.addActionListener {
                    todoService.updateTask(newTask.text, checkBox.isSelected)
                    updateProgressBar()
                }
                onNoteChanged = { note ->
                    todoService.updateNote(newTask.text, note)
                }
            }
            tasksPanel.add(taskPanel)
            tasksPanel.revalidate()
            tasksPanel.repaint()
            updateProgressBar()
        }

        // Câmp + buton de adăugare
        val addTaskField = JTextField()
        val addButton = JButton("Adaugă").apply {
            addActionListener {
                val text = addTaskField.text.trim()
                if (text.isNotEmpty()) {
                    if (todoService.taskExists(text)) {
                        JOptionPane.showMessageDialog(
                            null,
                            "Există deja un task cu acest nume.",
                            "Task duplicat",
                            JOptionPane.WARNING_MESSAGE
                        )
                        return@addActionListener
                    }

                    addNewTask(text)
                    addTaskField.text = ""
                }
            }
        }

        addTaskField.addActionListener {
            addButton.doClick() // când apasă ENTER, declanșăm același flux
        }

        // Buton de ștergere a celor bifate
        removeButton.addActionListener {
            val toRemove = mutableListOf<Component>()
            val taskTextsToRemove = mutableListOf<String>()

            tasksPanel.components.forEach {
                if (it is TaskWithNotePanel && it.checkBox.isSelected) {
                    toRemove.add(it)
                    taskTextsToRemove.add(it.getTaskText())
                }
            }
            // Scoate din UI
            toRemove.forEach { tasksPanel.remove(it) }
            // Scoate din service
            todoService.removeTasksByText(taskTextsToRemove)

            tasksPanel.revalidate()
            tasksPanel.repaint()
            updateProgressBar()
        }

        // Reîncărcăm task-urile existente din state
        todoService.getAllTasks().forEach { savedTask: Task ->
            val taskPanel = TaskWithNotePanel(project, savedTask).apply {
                checkBox.addActionListener {
                    todoService.updateTask(savedTask.text, checkBox.isSelected)
                    updateProgressBar()
                }
                onNoteChanged = { note ->
                    todoService.updateNote(savedTask.text, note)
                }
            }
            tasksPanel.add(taskPanel)
        }


        // Panel pentru textfield + buton “Adaugă”
        val inputPanel = JPanel(BorderLayout(5, 0)).apply {
            border = BorderFactory.createEmptyBorder(5, 5, 5, 5)
            add(addTaskField, BorderLayout.CENTER)
            add(addButton, BorderLayout.EAST)
        }

        // Panel de jos (progress + remove)
        val bottomPanel = JPanel(BorderLayout()).apply {
            border = BorderFactory.createEmptyBorder(5, 5, 5, 5)
            add(progressBar, BorderLayout.NORTH)
            add(removeButton, BorderLayout.SOUTH)
        }

        mainPanel.add(inputPanel, BorderLayout.NORTH)
        mainPanel.add(scrollPane, BorderLayout.CENTER)
        mainPanel.add(bottomPanel, BorderLayout.SOUTH)

        updateProgressBar()

        val content = ContentFactory.getInstance().createContent(mainPanel, "", false)
        toolWindow.contentManager.addContent(content)
    }
}
