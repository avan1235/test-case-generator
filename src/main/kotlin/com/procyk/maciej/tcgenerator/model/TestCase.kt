package com.procyk.maciej.tcgenerator.model

import java.lang.StringBuilder

data class TestCase(
        val testSteps: List<TestStep>
) {
    override fun toString() = with(StringBuilder(TEST_STEP_DELIMITER)) {
        this@TestCase.testSteps.forEach { step ->
            append(step)
            append(TEST_STEP_DELIMITER)
        }
        toString()
    }
}

private const val TEST_STEP_DELIMITER = "=============================================\n"