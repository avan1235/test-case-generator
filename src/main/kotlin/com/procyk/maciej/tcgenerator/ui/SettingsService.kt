package com.procyk.maciej.tcgenerator.ui

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.options.SearchableConfigurable
import com.intellij.ui.layout.panel
import com.procyk.maciej.tcgenerator.providers.jira.JiraConfigurationService
import com.procyk.maciej.tcgenerator.template.TemplateConfigurationService

@Service
class SettingsService : SearchableConfigurable {

    companion object {
        val instance: SettingsService
            get() = ServiceManager.getService(SettingsService::class.java)
    }

    private val panel = panel {
        row {
            button("Reset Jira Settings") {
                JiraConfigurationService.instance.resetState()
            }
        }
        row {
            button("Reset Template Settings") {
                TemplateConfigurationService.instance.resetState()
            }
        }
    }

    override fun createComponent() = panel

    override fun reset() {
        super.reset()
        JiraConfigurationService.instance.jira.rememberSettings = false
        TemplateConfigurationService.instance.template.rememberSettings = false
    }

    override fun isModified() = listOf(
        JiraConfigurationService.instance.jira.rememberSettings,
        TemplateConfigurationService.instance.template.rememberSettings,
    ).any { it }

    override fun apply() = Unit

    override fun getDisplayName() = "Test Case Generator"

    override fun getId() = "Test Case Generator"
}
