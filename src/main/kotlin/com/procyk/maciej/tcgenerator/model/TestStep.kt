package com.procyk.maciej.tcgenerator.model

data class TestStep(
        val stepDetails: String,
        val testData: String,
        val expectedResult: String,
) {
    override fun toString(): String {
        return "DETAILS:\n$stepDetails\nDATA: $testData\nEXPECTED RESULT:\n$expectedResult\n"
    }
}