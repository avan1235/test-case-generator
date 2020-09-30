package com.procyk.maciej.tcgenerator.providers.jira

import com.intellij.openapi.components.Service
import com.procyk.maciej.tcgenerator.model.TestCaseProviderRequesterFactory

@Service
class JiraRequesterFactory : TestCaseProviderRequesterFactory<JiraProvider>() {

    override fun createRequest() = JiraConfigurationRequest
}
