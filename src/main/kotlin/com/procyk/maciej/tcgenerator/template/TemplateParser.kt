package com.procyk.maciej.tcgenerator.template

import com.procyk.maciej.tcgenerator.model.TestCase
import com.procyk.maciej.tcgenerator.model.UserInput

interface TemplateParser {

    fun mergeWithTemplate(testCase: TestCase, userInput: UserInput): String
}
