package com.procyk.maciej.tcgenerator.providers.csv

import com.procyk.maciej.tcgenerator.model.TestCase
import com.procyk.maciej.tcgenerator.model.TestStep
import com.procyk.maciej.tcgenerator.model.collectMappedTestSteps

data class CsvTestStep(val stepDetails: String, val testData: String, val expectedResult: String)
data class CsvTestSummary(val summary: String)
data class CsvTestCase(val summary: CsvTestSummary, val steps: List<CsvTestStep>)

fun List<CsvTestStep>.toBaseModel(summary: CsvTestSummary): TestCase =
    collectMappedTestSteps(summary.summary) { TestStep(it.stepDetails, it.testData, it.expectedResult) }
