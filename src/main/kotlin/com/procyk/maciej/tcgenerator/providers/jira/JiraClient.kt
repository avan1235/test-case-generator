package com.procyk.maciej.tcgenerator.providers.jira

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.jackson.responseObject

class JiraClient(
    private val baseURL: String,
    private val authorization: JiraAuthorization
) {

    fun getJiraIssueId(issueKey: String): JiraIssueId {
        val (_, _, result) = jiraGetRequest("$baseURL/rest/api/2/issue/$issueKey")
            .responseObject<JiraIssueId>()
        return result.get()
    }

    fun getJiraIssuePart(issueId: Int, offset: Int): JiraIssue {
        val (_, _, result) =
            jiraGetRequest("$baseURL/rest/qtm/latest/teststep?&testCaseIssueId=$issueId&maxResults=10&offset=$offset")
                .responseObject<JiraIssue>()
        return result.get()
    }

    private fun jiraGetRequest(path: String): Request {
        return Fuel.get(path)
            .header(Headers.AUTHORIZATION to authorization.encodeToHeaderValue())
    }
}
