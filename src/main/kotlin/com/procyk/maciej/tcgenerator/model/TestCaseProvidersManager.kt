package com.procyk.maciej.tcgenerator.model

import com.intellij.openapi.components.ServiceManager
import com.procyk.maciej.tcgenerator.providers.csv.CsvRequesterFactory
import com.procyk.maciej.tcgenerator.providers.jira.JiraRequesterFactory

private val PROVIDERS_FACTORIES = listOf(
    JiraRequesterFactory::class,
    CsvRequesterFactory::class,
)

private val DEFAULT_PROVIDER_FACTORY = JiraRequesterFactory::class

object TestCaseProvidersManager {

    private val providersMap = PROVIDERS_FACTORIES
        .map { ServiceManager.getService(it.java) }
        .map { it.getUniqueName() to it }
        .toMap()

    fun getAvailableProvidersNames(): List<String> {
        val all = providersMap.keys.toMutableList()
        val selected = getProviderName()
        all.remove(selected)
        all.add(0, selected)
        return all.toList()
    }

    fun getProvider() = providersMap[getProviderName()] ?: ServiceManager.getService(DEFAULT_PROVIDER_FACTORY.java)

    fun getProviderName() = TestCaseProvidersConfigurationService.instance.state.providerName
}
