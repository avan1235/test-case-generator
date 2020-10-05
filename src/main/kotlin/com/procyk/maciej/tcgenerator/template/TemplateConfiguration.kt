package com.procyk.maciej.tcgenerator.template

import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.procyk.maciej.tcgenerator.util.validateFilePath
import java.io.File

data class TemplateConfiguration(
    var templateFilePath: String = "",
    var savedFileExtension: String = "",
    var rememberSettings: Boolean = false,
)

@Service
@State(name = "TemplateConfiguration", storages = [Storage("templateConfiguration.xml")])
class TemplateConfigurationService : PersistentStateComponent<TemplateConfiguration> {

    companion object {
        val instance: TemplateConfigurationService
            get() = ServiceManager.getService(TemplateConfigurationService::class.java)

        val templateFile: File?
            get() {
                return validateFilePath(instance.template.templateFilePath)
            }
    }

    private var template = TemplateConfiguration()

    override fun getState() = template

    override fun loadState(state: TemplateConfiguration) {
        template = state
    }
}
