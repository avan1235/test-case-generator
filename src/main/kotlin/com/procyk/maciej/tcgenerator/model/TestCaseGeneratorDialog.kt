package com.procyk.maciej.tcgenerator.model

import com.procyk.maciej.tcgenerator.UserInputDialog
import com.procyk.maciej.tcgenerator.util.valid

class TestCaseGeneratorDialog<P : TestCaseProvider>(private val extraUserInputCollector: UserInputCollector<P>) {

    fun generateTestCase(): TestCase? {
        val userInput = UserInput()
        UserInputDialog(userInput).showAndGet().valid() ?: return null
        return extraUserInputCollector.generateTestCaseProvider()?.provideTestCaseForUserInput(userInput)
    }
}