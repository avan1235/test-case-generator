package com.procyk.maciej.tcgenerator.template

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.layout.LayoutBuilder
import com.intellij.ui.layout.panel
import com.procyk.maciej.tcgenerator.model.ConfigurationRequest
import com.procyk.maciej.tcgenerator.template.TemplateConfigurationService.Companion.instance
import com.procyk.maciej.tcgenerator.util.Valid
import com.procyk.maciej.tcgenerator.util.validate

object TemplateConfigurationRequest : ConfigurationRequest {

    override fun configure(): Valid? {
        if (!instance.state.rememberSettings) {
            return TemplateConfigurationDialog(instance.state).showAndGet().validate()
        }
        return Valid
    }
}

internal class TemplateConfigurationDialog(private val templateConfiguration: TemplateConfiguration) :
    DialogWrapper(true) {

    companion object {
        fun createInternal(templateConfiguration: TemplateConfiguration): LayoutBuilder.() -> Unit = {
            row("Template file") {
                textFieldWithBrowseButton(
                    browseDialogTitle = "Select",
                    fileChooserDescriptor = FileChooserDescriptorFactory.createSingleFileDescriptor(),
                    prop = templateConfiguration::templateFilePath
                ).focused()
            }
            row("Saved file extension") {
                textField(templateConfiguration::savedFileExtension)
            }
            row {
                checkBox(
                    "Remember",
                    templateConfiguration::rememberSettings
                )
            }
        }
    }

    init {
        init()
        title = "Configure Template Engine"
    }

    override fun createCenterPanel() = panel {
        createInternal(templateConfiguration)()
    }
}
