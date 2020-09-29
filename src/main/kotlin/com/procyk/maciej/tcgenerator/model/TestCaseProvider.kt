package com.procyk.maciej.tcgenerator.model

interface TestCaseProvider {

    fun provideTestCaseForUserInput(userInput: UserInput): TestCase?
}

inline fun <T> List<T>.collectMappedTestSteps(details: String, mapper: (T) -> TestStep) = TestCase(details, map(mapper))
