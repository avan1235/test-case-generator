package com.procyk.maciej.tcgenerator.model

import com.intellij.openapi.components.ServiceManager
import com.procyk.maciej.tcgenerator.providers.csv.CsvRequesterFactory
import com.procyk.maciej.tcgenerator.providers.jira.JiraRequesterFactory

private val PROVIDERS_FACTORIES = listOf(
    JiraRequesterFactory::class,
    CsvRequesterFactory::class,
)

object TestCaseProvidersManager {

    private val providersMap = PROVIDERS_FACTORIES
        .map { ServiceManager.getService(it.java) }
        .map { it.getUniqueName() to it }
        .toMap()

    fun getAvailableProviders() = providersMap.keys

    fun getProvider() = providersMap[TestCaseProvidersConfigurationService.instance.providers.providerName]
}
