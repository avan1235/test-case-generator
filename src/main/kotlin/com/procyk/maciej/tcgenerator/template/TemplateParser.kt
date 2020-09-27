package com.procyk.maciej.tcgenerator.template

import com.procyk.maciej.tcgenerator.model.TestCase
import com.procyk.maciej.tcgenerator.model.UserInput
import java.io.File

interface TemplateParser {

    fun mergeWithTemplate(templateFile: File, testCase: TestCase, userInput: UserInput): String
}
