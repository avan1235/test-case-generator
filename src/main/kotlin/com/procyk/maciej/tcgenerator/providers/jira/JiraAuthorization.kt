package com.procyk.maciej.tcgenerator.providers.jira

import com.github.kittinunf.fuel.util.encodeBase64
import java.nio.charset.StandardCharsets

data class JiraAuthorization(
    val username: String,
    val password: String,
) {
    fun encodeToHeaderValue(): String {
        val encodedBytes = "$username:$password"
            .toByteArray(charset = StandardCharsets.ISO_8859_1)
            .encodeBase64()
        return "Basic ${String(encodedBytes)}"
    }
}
