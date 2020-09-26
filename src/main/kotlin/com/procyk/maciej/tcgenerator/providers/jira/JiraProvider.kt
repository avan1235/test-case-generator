package com.procyk.maciej.tcgenerator.providers.jira

import com.procyk.maciej.tcgenerator.model.TestCase
import com.procyk.maciej.tcgenerator.model.TestCaseProvider

class JiraProvider(jiraBaseUrl: String, jiraAuthorization: JiraAuthorization) : TestCaseProvider {

    private val client = JiraClient(jiraBaseUrl, jiraAuthorization)

    override fun provideTestCaseForUserInput(userInput: String): TestCase {
        val id = client.getJiraIssueId(userInput).id
        var lastReceived: Int
        var alreadyReceived = 0
        val jiraStepUnits = mutableListOf<JiraTestStep>()
        do {
            val last = client.getJiraIssuePart(id, alreadyReceived)
            lastReceived = last.result.stepUnits.size
            alreadyReceived += lastReceived
            jiraStepUnits += last.result.stepUnits
        } while (lastReceived > 0)
        return jiraStepUnits.sortedBy { it.seqNo }.toBaseModel()
    }
}
