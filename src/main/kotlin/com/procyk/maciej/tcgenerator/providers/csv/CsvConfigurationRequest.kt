package com.procyk.maciej.tcgenerator.providers.csv

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.layout.panel
import com.procyk.maciej.tcgenerator.model.ConfigurationRequest
import com.procyk.maciej.tcgenerator.model.TestCaseProviderRequester
import com.procyk.maciej.tcgenerator.util.Valid
import com.procyk.maciej.tcgenerator.util.validate

class CsvConfigurationRequest : TestCaseProviderRequester<CsvProvider>, ConfigurationRequest {

    private val configuration = CsvConfiguration()

    override fun generateTestCaseProvider(): CsvProvider? {
        configure() ?: return null
        return CsvProvider(configuration)
    }

    override fun configure(): Valid? {
        return CsvConfigurationDialog(configuration).showAndGet().validate()
    }
}

private class CsvConfigurationDialog(private val configuration: CsvConfiguration) : DialogWrapper(true) {

    init {
        init()
        title = "Generate Test Case"
    }

    override fun createCenterPanel() = panel {
        row("Data file path:") {
            textFieldWithBrowseButton(
                browseDialogTitle = "Select",
                fileChooserDescriptor = FileChooserDescriptorFactory.createSingleFileDescriptor(),
                prop = configuration::inputFilePath
            )
        }
    }
}
