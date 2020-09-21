package com.github.avan1235.testtemplates.services

import com.intellij.openapi.project.Project
import com.github.avan1235.testtemplates.MyBundle

class MyProjectService(project: Project) {

    init {
        println(MyBundle.message("projectService", project.name))
    }
}
