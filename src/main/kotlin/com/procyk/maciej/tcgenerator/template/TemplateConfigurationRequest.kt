package com.procyk.maciej.tcgenerator.template

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.layout.panel
import com.procyk.maciej.tcgenerator.model.ConfigurationRequest
import com.procyk.maciej.tcgenerator.template.TemplateConfigurationService.Companion.instance
import com.procyk.maciej.tcgenerator.util.Valid
import com.procyk.maciej.tcgenerator.util.validate

object TemplateConfigurationRequest : ConfigurationRequest {

    override fun configure(): Valid? {
        if (!instance.template.rememberSettings) {
            return TemplateConfigurationDialog().showAndGet().validate()
        }
        return Valid
    }
}

private class TemplateConfigurationDialog : DialogWrapper(true) {
    init {
        init()
        title = "Configure Template Engine"
    }

    override fun createCenterPanel() = panel {
        row("Template file") {
            textFieldWithBrowseButton(
                browseDialogTitle = "Select",
                fileChooserDescriptor = FileChooserDescriptorFactory.createSingleFileDescriptor(),
                prop = instance.template::templateFilePath
            )
        }
        row("Saved file extension") {
            textField(instance.template::savedFileExtension)
        }
        row {
            checkBox(
                "Remember",
                instance.template::rememberSettings
            )
        }
    }
}
