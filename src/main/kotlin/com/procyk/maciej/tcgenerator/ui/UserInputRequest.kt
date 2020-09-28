package com.procyk.maciej.tcgenerator.ui

import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.layout.panel
import com.procyk.maciej.tcgenerator.model.ConfigurationRequest
import com.procyk.maciej.tcgenerator.model.UserInput
import com.procyk.maciej.tcgenerator.util.Valid
import com.procyk.maciej.tcgenerator.util.validate

class UserInputRequest(private val userInput: UserInput) : ConfigurationRequest {

    override fun configure(): Valid? {
        return UserInputDialog(userInput).showAndGet().validate()
    }
}

private class UserInputDialog(private val userInput: UserInput) : DialogWrapper(true) {
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
