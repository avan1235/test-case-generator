package com.procyk.maciej.tcgenerator.providers.csv

import com.procyk.maciej.tcgenerator.util.ValidationException

class CsvParsingException(error: String) : ValidationException("Csv parsing error: $error")
