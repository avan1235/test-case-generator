package com.procyk.maciej.tcgenerator.model

interface UserInputCollector<P : TestCaseProvider> {

    fun generateTestCaseProvider(): P?
}
