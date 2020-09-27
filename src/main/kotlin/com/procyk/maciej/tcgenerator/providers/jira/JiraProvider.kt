package com.procyk.maciej.tcgenerator.providers.jira

import com.procyk.maciej.tcgenerator.model.TestCase
import com.procyk.maciej.tcgenerator.model.TestCaseProvider
import com.procyk.maciej.tcgenerator.model.UserInput

class JiraProvider(jiraConfiguration: JiraConfiguration, jiraCredentials: JiraCredentials) : TestCaseProvider {

    private val client = JiraClient(jiraConfiguration, jiraCredentials)

    override fun provideTestCaseForUserInput(userInput: UserInput): TestCase {
        val id = client.getJiraIssueId(userInput)
        var lastReceived: Int
        var alreadyReceived = 0
        val jiraStepUnits = mutableListOf<JiraTestStep>()
        do {
            val last = client.getJiraIssuePart(id, alreadyReceived)
            lastReceived = last.result.stepUnits.size
            alreadyReceived += lastReceived
            jiraStepUnits += last.result.stepUnits
        } while (lastReceived > 0)
        return jiraStepUnits.sortedBy { it.seqNo }.toBaseModel(id)
    }
}
