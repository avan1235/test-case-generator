package com.procyk.maciej.tcgenerator.model

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage

data class TestCaseProvidersConfiguration(
    var providerName: String = "Jira"
)

@Service
@State(name = "ProvidersConfiguration", storages = [Storage("providerConfiguration.xml")])
class TestCaseProvidersConfigurationService :
    PersistentStateComponent<TestCaseProvidersConfiguration> {

    companion object {
        val instance: TestCaseProvidersConfigurationService
            get() = ServiceManager.getService(TestCaseProvidersConfigurationService::class.java)
    }

    private var providers = TestCaseProvidersConfiguration()

    override fun getState() = providers

    override fun loadState(state: TestCaseProvidersConfiguration) {
        providers = state
    }
}
