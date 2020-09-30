package com.procyk.maciej.tcgenerator.providers.csv

import com.intellij.openapi.components.Service
import com.procyk.maciej.tcgenerator.model.TestCaseProviderRequesterFactory

@Service
class CsvRequesterFactory : TestCaseProviderRequesterFactory<CsvProvider>() {

    override fun createRequest() = CsvConfigurationRequest()
}
