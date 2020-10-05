package com.procyk.maciej.tcgenerator.providers.jira

import com.intellij.credentialStore.askCredentials
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.layout.LayoutBuilder
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
        return JiraProvider(instance.state, JiraCredentials(password))
    }

    override fun configure(): Valid? {
        if (!instance.state.rememberSettings) {
            return JiraConfigurationDialog(instance.state).showAndGet().validate()
        }
        return Valid
    }
}

internal class JiraConfigurationDialog(private val configuration: JiraConfiguration) : DialogWrapper(true) {
    companion object {
        fun createInternal(configuration: JiraConfiguration): LayoutBuilder.() -> Unit = {
            row("Jira URL:") {
                textField(configuration::url).focused()
            }
            row("Jira username:") {
                textField(configuration::username)
            }
            row {
                checkBox(
                    "Remember",
                    configuration::rememberSettings
                )
            }
        }
    }

    init {
        init()
        title = "Generate Test Case"
    }

    override fun createCenterPanel() = panel {
        createInternal(configuration)()
    }
}
