package com.procyk.maciej.tcgenerator

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.layout.panel
import com.procyk.maciej.tcgenerator.model.UserInput

class UserInputDialog(private val userInput: UserInput) : DialogWrapper(true) {
    init {
        init()
        title = "Test Case Specification"
    }

    override fun createCenterPanel() = panel {
        row("Test Case:") {
            textField(userInput::value)
        }
    }
}
