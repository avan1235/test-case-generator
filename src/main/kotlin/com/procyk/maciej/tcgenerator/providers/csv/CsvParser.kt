package com.procyk.maciej.tcgenerator.providers.csv

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.util.MalformedCSVException
import com.procyk.maciej.tcgenerator.model.TestCase
import java.io.File

private const val CSV_DATA_COLUMNS = 3

object CsvParser {

    fun parseFile(file: File): TestCase {
        val lines = file.readLines()
        if (lines.isEmpty()) {
            throw CsvParsingException("No summary data in first line")
        }
        val summary = CsvTestSummary(lines[0])
        val dataInput = lines.drop(1).joinToString("\n")
        return try {
            csvReader {
                skipEmptyLine = true
            }.readAll(dataInput)
                .map { it.toTestStep() }
                .toBaseModel(summary)
        } catch (e: MalformedCSVException) {
            throw CsvParsingException(e.message ?: "Unknown error")
        }
    }
}

private fun List<String>.toTestStep(): CsvTestStep {
    if (this.size != CSV_DATA_COLUMNS) {
        throw CsvParsingException("Invalid number of columns in csv file. Expected 3: DETAILS, DATA, EXPECTED")
    }
    return CsvTestStep(this[0], this[1], this[2])
}
