package com.github.compilebulai.hack.toolWindow

import com.github.compilebulai.hack.codeTask.CodeTaskAssociator
import com.intellij.openapi.project.Project
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.*
import com.github.compilebulai.hack.state.Task


class TaskWithNotePanel(
    private val project: Project,
    private val task: Task
) : JPanel() {

    val checkBox = JCheckBox(task.text).apply {
        isSelected = task.completed
    }

    var onNoteChanged: ((String) -> Unit)? = null

    init {
        layout = BorderLayout()
        isOpaque = false
        border = BorderFactory.createEmptyBorder(2, 10, 2, 10)

        // Panel pentru stânga: checkbox
        val leftPanel = JPanel().apply {
            layout = BoxLayout(this, BoxLayout.X_AXIS)
            isOpaque = false
            add(checkBox)
        }

        // Panel pentru dreapta: butoane Note + GoTo
        val rightPanel = JPanel().apply {
            layout = BoxLayout(this, BoxLayout.X_AXIS)
            isOpaque = false

            add(Box.createRigidArea(Dimension(10, 0)))

            val noteButton = JButton("Note").apply {
                toolTipText = "Adaugă sau editează o notă"
                addActionListener { showNoteDialog() }
            }
            add(noteButton)

            add(Box.createRigidArea(Dimension(10, 0)))

            val navigateButton = JButton("GoTo Code").apply {
                toolTipText = "Navighează la codul asociat"
                addActionListener {
                    val codeAssociator = project.getService(CodeTaskAssociator::class.java)
                    if (task.codeLocation != null) {
                        codeAssociator.navigateToCode(task.text)
                    } else {
                        JOptionPane.showMessageDialog(
                            this@TaskWithNotePanel,
                            "Acest task nu are cod asociat.",
                            "Info",
                            JOptionPane.INFORMATION_MESSAGE
                        )
                    }
                }
            }
            add(navigateButton)
        }

        add(leftPanel, BorderLayout.WEST)
        add(rightPanel, BorderLayout.EAST)
    }

    private fun showNoteDialog() {
        val textArea = JTextArea(5, 30).apply {
            text = task.note.orEmpty()
        }

        val scrollPane = JScrollPane(textArea)
        val result = JOptionPane.showConfirmDialog(
            this,
            scrollPane,
            "Notă pentru: \"${checkBox.text}\"",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        )

        if (result == JOptionPane.OK_OPTION) {
            val newNote = textArea.text.trim()
            onNoteChanged?.invoke(newNote)
        }
    }

    fun getTaskText(): String = task.text
}
