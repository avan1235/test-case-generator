package com.procyk.maciej.tcgenerator

import com.intellij.openapi.actionSystem.ActionPlaces
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

class MyAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) = doAction(e)

    override fun update(e: AnActionEvent) = doAction(e)

    private fun doAction(e: AnActionEvent) {
        val presentation = e.presentation
        if (e.place == ActionPlaces.PROJECT_VIEW_POPUP) {
            presentation.text = "popup her :D"
        } else if (e.place == ActionPlaces.PROJECT_VIEW_TOOLBAR) {
            presentation.text = "toolbar here :)"
        }
    }
}
