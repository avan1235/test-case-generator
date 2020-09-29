package com.procyk.maciej.tcgenerator.providers.csv

import com.procyk.maciej.tcgenerator.model.TestCase
import com.procyk.maciej.tcgenerator.model.TestCaseProvider
import com.procyk.maciej.tcgenerator.model.UserInput
import com.procyk.maciej.tcgenerator.util.validateFilePath

class CsvProvider(private val csvConfiguration: CsvConfiguration) : TestCaseProvider {

    override fun provideTestCaseForUserInput(userInput: UserInput): TestCase? {
        return validateFilePath(csvConfiguration.inputFilePath)?.let { file ->
            CsvParser.parseFile(file)
        }
    }
}
