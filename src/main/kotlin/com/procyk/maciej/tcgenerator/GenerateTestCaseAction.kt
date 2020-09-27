package com.procyk.maciej.tcgenerator

import com.github.kittinunf.fuel.core.HttpException
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFile
import com.procyk.maciej.tcgenerator.model.TestCaseGeneratorDialog
import com.procyk.maciej.tcgenerator.providers.jira.JiraConfigurationDialog
import com.procyk.maciej.tcgenerator.util.showNotification

class GenerateTestCaseAction : AnAction() {

    override fun actionPerformed(event: AnActionEvent) {
        val directory = extractActionDirectory(event)
        if (directory == null) {
            event.presentation.isVisible = false
            return
        }

        val message = try {
            TestCaseGeneratorDialog(JiraConfigurationDialog()).generateTestCase().toString()
        } catch (e: HttpException) {
            e.message
        }
        showNotification(message ?: "No data")
    }

    override fun update(event: AnActionEvent) {
        val directory = extractActionDirectory(event)
        if (directory == null) {
            event.presentation.isVisible = false
            return
        }
    }
}

private fun extractActionDirectory(event: AnActionEvent): PsiDirectory? =
    when (val clicked = event.getData(CommonDataKeys.PSI_ELEMENT)) {
        is PsiDirectory -> clicked
        is PsiFile -> clicked.containingDirectory
        else -> null
    }
