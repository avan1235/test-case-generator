package com.procyk.maciej.tcgenerator.ui

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.options.SearchableConfigurable
import com.intellij.ui.layout.panel
import com.procyk.maciej.tcgenerator.model.TestCaseProvidersConfigurationService
import com.procyk.maciej.tcgenerator.model.TestCaseProvidersManager
import com.procyk.maciej.tcgenerator.providers.jira.JiraConfigurationDialog
import com.procyk.maciej.tcgenerator.providers.jira.JiraConfigurationService
import com.procyk.maciej.tcgenerator.template.TemplateConfigurationDialog
import com.procyk.maciej.tcgenerator.template.TemplateConfigurationService
import javax.swing.DefaultComboBoxModel

@Service
class SettingsService : SearchableConfigurable {

    private var jiraConfiguration = JiraConfigurationService.instance.state.copy()

    private var templateConfiguration = TemplateConfigurationService.instance.template.copy()

    private var providersConfiguration = TestCaseProvidersConfigurationService.instance.providers.copy()

    companion object {
        val instance: SettingsService
            get() = ServiceManager.getService(SettingsService::class.java)
    }

    private val panel by lazy {
        panel {
            JiraConfigurationDialog.createInternal(jiraConfiguration)()
            TemplateConfigurationDialog.createInternal(templateConfiguration)()
            row {
                comboBox(
                    DefaultComboBoxModel(TestCaseProvidersManager.getAvailableProviders().toTypedArray()),
                    providersConfiguration::providerName
                )
            }
        }
    }

    override fun createComponent() = panel

    override fun reset() {
        super.reset()
        jiraConfiguration = JiraConfigurationService.instance.state.copy()
        templateConfiguration = TemplateConfigurationService.instance.template.copy()
        providersConfiguration = TestCaseProvidersConfigurationService.instance.providers.copy()
    }

    override fun isModified() = listOf(
        JiraConfigurationService.instance.state == jiraConfiguration,
        TemplateConfigurationService.instance.template == templateConfiguration,
        TestCaseProvidersConfigurationService.instance.providers == providersConfiguration
    ).any { !it }

    override fun apply() {
        JiraConfigurationService.instance.loadState(jiraConfiguration)
        TemplateConfigurationService.instance.loadState(templateConfiguration)
        TestCaseProvidersConfigurationService.instance.loadState(providersConfiguration)
    }

    override fun getDisplayName() = "Test Case Generator"

    override fun getId() = "Test Case Generator"
}
