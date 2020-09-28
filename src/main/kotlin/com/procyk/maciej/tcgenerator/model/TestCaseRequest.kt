package com.procyk.maciej.tcgenerator.model

import com.procyk.maciej.tcgenerator.template.TemplateConfigurationRequest
import com.procyk.maciej.tcgenerator.ui.UserInputRequest
import com.procyk.maciej.tcgenerator.util.throwOnNull

class TestCaseRequest<P : TestCaseProvider> private constructor(
    private val userInput: UserInput,
    private val testCaseProviderRequester: TestCaseProviderRequester<P>,
) {

    companion object {
        fun <T : TestCaseProvider> collectUserInputToAndGetGenerator(
            userInput: UserInput,
            testCaseProviderRequester: TestCaseProviderRequester<T>
        ): TestCaseRequest<T>? {
            UserInputRequest(userInput).configure().throwOnNull("User input canceled")
            TemplateConfigurationRequest.configure().throwOnNull("Template input canceled")
            return TestCaseRequest(userInput, testCaseProviderRequester)
        }
    }

    fun generateTestCase(): TestCase? {
        return testCaseProviderRequester.generateTestCaseProvider()?.provideTestCaseForUserInput(userInput)
    }
}
