package com.procyk.maciej.tcgenerator.model

interface TestCaseProvider {

    fun provideTestCaseForUserInput(userInput: String): TestCase
}

inline fun <T> List<T>.collectMappedTestSteps(mapper: (T) -> TestStep) = TestCase(this.map(mapper))
