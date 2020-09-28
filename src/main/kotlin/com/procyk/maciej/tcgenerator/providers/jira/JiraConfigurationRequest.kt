package com.procyk.maciej.tcgenerator.providers.jira

import com.intellij.credentialStore.askCredentials
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.layout.panel
import com.procyk.maciej.tcgenerator.model.ConfigurationRequest
import com.procyk.maciej.tcgenerator.model.TestCaseProviderRequester
import com.procyk.maciej.tcgenerator.providers.jira.JiraConfigurationService.Companion.instance
import com.procyk.maciej.tcgenerator.util.Valid
import com.procyk.maciej.tcgenerator.util.validate

object JiraConfigurationRequest : TestCaseProviderRequester<JiraProvider>, ConfigurationRequest {

    override fun generateTestCaseProvider(): JiraProvider? {
        configure() ?: return null
        val password = askCredentials(
            project = null,
            dialogTitle = "Enter Jira Password",
            passwordFieldLabel = "Jira Password",
            attributes = JIRA_CREDENTIAL_ATTRIBUTES,
            isSaveOnOk = true,
            isCheckExistingBeforeDialog = true,
            isResetPassword = false
        )?.credentials?.getPasswordAsString() ?: ""
        return JiraProvider(instance.jira, JiraCredentials(password))
    }

    override fun configure(): Valid? {
        if (!instance.jira.rememberSettings) {
            return JiraConfigurationDialog().showAndGet().validate()
        }
        return Valid
    }
}

private class JiraConfigurationDialog : DialogWrapper(true) {
    init {
        init()
        title = "Generate Test Case"
    }

    override fun createCenterPanel() = panel {
        row("Jira URL:") {
            textField(instance.jira::url)
        }
        row("Jira username:") {
            textField(instance.jira::username)
        }
        row {
            checkBox(
                "Remember",
                instance.jira::rememberSettings
            )
        }
    }
}
