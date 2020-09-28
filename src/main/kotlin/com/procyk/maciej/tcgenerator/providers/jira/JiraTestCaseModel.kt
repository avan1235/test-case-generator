package com.procyk.maciej.tcgenerator.providers.jira

import com.procyk.maciej.tcgenerator.model.TestCase
import com.procyk.maciej.tcgenerator.model.TestStep
import com.procyk.maciej.tcgenerator.model.collectMappedTestSteps

data class JiraFields(val summary: String)
data class JiraIssueId(val id: Int, val fields: JiraFields)
data class JiraTestStep(val stepDetails: String, val testData: String, val expectedResult: String, val seqNo: Int)
data class JiraResult(val stepUnits: List<JiraTestStep>)
data class JiraIssueRequest(val success: Boolean, val result: JiraResult)

fun List<JiraTestStep>.toBaseModel(id: JiraIssueId): TestCase =
    collectMappedTestSteps(id.fields.summary) { TestStep(it.stepDetails, it.testData, it.expectedResult) }
