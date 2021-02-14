package com.procyk.maciej.tcgenerator.actions

import com.github.kittinunf.fuel.core.HttpException
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.fileTypes.PlainTextLanguage
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiFileFactory
import com.intellij.util.IncorrectOperationException
import com.procyk.maciej.tcgenerator.model.TestCaseProviderRequester
import com.procyk.maciej.tcgenerator.model.TestCaseProvidersManager
import com.procyk.maciej.tcgenerator.model.TestCaseRequest.Companion.collectUserInputToAndGetGenerator
import com.procyk.maciej.tcgenerator.model.UserInput
import com.procyk.maciej.tcgenerator.template.TemplateConfigurationService
import com.procyk.maciej.tcgenerator.template.TemplateParser
import com.procyk.maciej.tcgenerator.template.freemarker.FreemarkerTemplateParser
import com.procyk.maciej.tcgenerator.util.ValidationException
import com.procyk.maciej.tcgenerator.util.notifyOnNull
import com.procyk.maciej.tcgenerator.util.showNotification
import com.procyk.maciej.tcgenerator.util.throwOnNull
import java.io.File

class GenerateTestCaseAction : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        val directory = manageVisibility(event) ?: return
        val project = event.getData(CommonDataKeys.PROJECT).notifyOnNull("Null project") ?: return
        val userInput = UserInput()
        val requester = getTestCaseProviderRequester()

        try {
            val testCase = collectUserInputToAndGetGenerator(userInput, requester)
                ?.generateTestCase().throwOnNull("Test Case Generator action canceled")
            val template = TemplateConfigurationService.templateFile
                .throwOnNull("Invalid template file path")
            val parser = getTemplateParser(template)
            val merged = parser.mergeWithTemplate(testCase, userInput)
            val file = PsiFileFactory.getInstance(project).createFileFromText(
                userInput.value + getNormalizedFileExtension(),
                PlainTextLanguage.INSTANCE,
                merged
            )
            directory.add(file)
        } catch (e: HttpException) {
            showNotification("Connection error: ${e.message}")
        } catch (e: IncorrectOperationException) {
            showNotification("Invalid operation: ${e.message}")
        } catch (e: ValidationException) {
            showNotification("Test Case Generation process canceled: ${e.message}")
        } catch (@Suppress("TooGenericExceptionCaught") e: Exception) {
            showNotification(
                "Unknown error. Please report with stacktrace:\n" +
                    e.stackTrace.joinToString("\n") { it.toString() }
            )
        }
    }

    override fun update(event: AnActionEvent) {
        manageVisibility(event)
    }
}

private fun getTemplateParser(template: File): TemplateParser = FreemarkerTemplateParser(template)

private fun getTestCaseProviderRequester(): TestCaseProviderRequester<*> =
    TestCaseProvidersManager.getProvider().createRequest()

private fun getNormalizedFileExtension(): String {
    val input = TemplateConfigurationService.instance.state.savedFileExtension
    return if (input.startsWith('.')) input else ".$input"
}

private fun extractActionDirectory(event: AnActionEvent): PsiDirectory? =
    when (val clicked = event.getData(CommonDataKeys.PSI_ELEMENT)) {
        is PsiDirectory -> clicked
        is PsiFile -> clicked.containingDirectory
        else -> null
    }

private fun manageVisibility(event: AnActionEvent): PsiDirectory? {
    val directory = extractActionDirectory(event)
    if (directory == null) {
        event.presentation.isVisible = false
        return null
    }
    return directory
}
