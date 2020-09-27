package com.procyk.maciej.tcgenerator.services

import com.intellij.openapi.project.Project
import com.procyk.maciej.tcgenerator.MyBundle

class ProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
