package com.procyk.maciej.tcgenerator.providers.jira

import com.intellij.credentialStore.askCredentials
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory.createSingleFileDescriptor
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.layout.panel
import com.procyk.maciej.tcgenerator.model.UserInputCollector
import com.procyk.maciej.tcgenerator.providers.jira.JiraConfigurationService.Companion.instance
import com.procyk.maciej.tcgenerator.util.valid

class JiraConfigurationDialog : DialogWrapper(true), UserInputCollector<JiraProvider> {
    init {
        init()
        title = "Generate Test Case"
    }

    override fun createCenterPanel() = panel {
        row("Jira URL:") {
            textField(instance.jiraConfiguration::url)
        }
        row("Jira username:") {
            textField(instance.jiraConfiguration::username)
        }
        row("Template file:") {
            textFieldWithBrowseButton(
                    browseDialogTitle = "Select",
                    fileChooserDescriptor = createSingleFileDescriptor("ftl"),
                    prop = instance.jiraConfiguration::templateFilePath
            )
        }
    }

    override fun generateTestCaseProvider(): JiraProvider? {
        showAndGet().valid() ?: return null
        val password = askCredentials(
                project = null,
                dialogTitle = "Enter Jira Password",
                passwordFieldLabel = "Jira Password",
                attributes = JIRA_CREDENTIAL_ATTRIBUTES,
                isSaveOnOk = false,
                isCheckExistingBeforeDialog = false,
                isResetPassword = false
        )?.credentials?.getPasswordAsString() ?: ""
        return JiraProvider(instance.jiraConfiguration, JiraCredentials(password))
    }
}
