package com.procyk.maciej.tcgenerator.providers.jira

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Headers
import com.github.kittinunf.fuel.core.Request
import com.github.kittinunf.fuel.jackson.responseObject
import com.github.kittinunf.fuel.util.encodeBase64
import com.procyk.maciej.tcgenerator.model.UserInput
import java.nio.charset.StandardCharsets

class JiraClient(
    private val configuration: JiraConfiguration,
    private val credentials: JiraCredentials
) {

    fun getJiraIssueId(issueKey: UserInput): JiraIssueId {
        val url = configuration.standardizedUrl()
        val requestPath = "$url/rest/api/2/issue/${issueKey.value}"
        val (_, _, result) = jiraAuthorizedGetRequest(requestPath)
            .responseObject<JiraIssueId>()
        return result.get()
    }

    fun getJiraIssuePart(issueId: JiraIssueId, offset: Int): JiraIssue {
        val url = configuration.standardizedUrl()
        val requestPath = "$url/rest/qtm/latest/teststep?&testCaseIssueId=${issueId.id}&maxResults=10&offset=$offset"
        val (_, _, result) = jiraAuthorizedGetRequest(requestPath).responseObject<JiraIssue>()
        return result.get()
    }

    private fun jiraAuthorizedGetRequest(path: String): Request {
        return Fuel.get(path).header(
            Headers.AUTHORIZATION to
                encodeToHeaderValue(configuration.username, credentials.password)
        )
    }
}

private fun encodeToHeaderValue(username: String, password: String): String {
    val encodedBytes = "$username:$password"
        .toByteArray(charset = StandardCharsets.ISO_8859_1)
        .encodeBase64()
    return "Basic ${String(encodedBytes)}"
}

private fun JiraConfiguration.standardizedUrl() =
    if (url.endsWith('/')) url.subSequence(0 until url.length - 1)
    else url
