package com.procyk.maciej.tcgenerator.providers.jira

import com.procyk.maciej.tcgenerator.model.TestCase
import com.procyk.maciej.tcgenerator.model.TestStep
import com.procyk.maciej.tcgenerator.model.collectMappedTestSteps

data class JiraIssueId(val id: Int)
data class JiraTestStep(val stepDetails: String, val testData: String, val expectedResult: String, val seqNo: Int)
data class JiraResult(val stepUnits: List<JiraTestStep>)
data class JiraIssue(val success: Boolean, val result: JiraResult)

fun List<JiraTestStep>.toBaseModel(): TestCase
        = this.collectMappedTestSteps { TestStep(it.stepDetails, it.testData, it.expectedResult) }