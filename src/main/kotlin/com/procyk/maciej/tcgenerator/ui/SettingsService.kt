package com.procyk.maciej.tcgenerator.ui

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.options.SearchableConfigurable
import com.intellij.ui.layout.panel
import com.procyk.maciej.tcgenerator.model.TestCaseProvidersConfigurationService
import com.procyk.maciej.tcgenerator.model.TestCaseProvidersManager
import com.procyk.maciej.tcgenerator.providers.jira.JiraConfiguration
import com.procyk.maciej.tcgenerator.providers.jira.JiraConfigurationService
import com.procyk.maciej.tcgenerator.template.TemplateConfiguration
import com.procyk.maciej.tcgenerator.template.TemplateConfigurationService
import javax.swing.DefaultComboBoxModel

@Service
class SettingsService : SearchableConfigurable {

    companion object {
        val instance: SettingsService
            get() = ServiceManager.getService(SettingsService::class.java)
    }

    override fun createComponent() = panel {
        row("Jira Configuration") {
            button("Reset") {
                JiraConfigurationService.instance.loadState(JiraConfiguration())
            }
        }
        row("Template Configuration") {
            button("Reset") {
                TemplateConfigurationService.instance.loadState(TemplateConfiguration())
            }
        }
        row {
            comboBox(
                DefaultComboBoxModel(TestCaseProvidersManager.getAvailableProviders().toTypedArray()),
                TestCaseProvidersConfigurationService.instance.state::providerName
            )
        }
    }

    override fun isModified() = false

    override fun apply() = Unit

    override fun getDisplayName() = "Test Case Generator"

    override fun getId() = "Test Case Generator"
}
