package com.procyk.maciej.tcgenerator.listeners

import com.intellij.openapi.project.Project
import com.intellij.openapi.project.ProjectManagerListener
import com.procyk.maciej.tcgenerator.services.ProjectService

internal class MyProjectManagerListener : ProjectManagerListener {

    override fun projectOpened(project: Project) {
        project.getService(ProjectService::class.java)
    }
}
