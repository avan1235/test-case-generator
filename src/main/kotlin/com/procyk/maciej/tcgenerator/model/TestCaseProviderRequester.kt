package com.procyk.maciej.tcgenerator.model

interface TestCaseProviderRequester<P : TestCaseProvider> {

    fun generateTestCaseProvider(): P?
}
