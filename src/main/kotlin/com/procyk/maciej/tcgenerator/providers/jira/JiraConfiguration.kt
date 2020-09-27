package com.procyk.maciej.tcgenerator.providers.jira

import com.intellij.openapi.components.Service
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

data class JiraConfiguration(
    var url: String = "",
    var username: String = "",
    var templateFilePath: String = "",
)

@Service
@State(name = "JiraConfiguration", storages = [Storage("jiraConfiguration.xml")])
class JiraConfigurationService : PersistentStateComponent<JiraConfiguration> {

    companion object {
        val instance: JiraConfigurationService
            get() = ServiceManager.getService(JiraConfigurationService::class.java)
    }

    var jiraConfiguration = JiraConfiguration()

    override fun getState() = jiraConfiguration

    override fun loadState(state: JiraConfiguration) {
        jiraConfiguration = state
    }
}
