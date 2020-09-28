package com.procyk.maciej.tcgenerator.template.freemarker

import com.procyk.maciej.tcgenerator.model.TestCase
import com.procyk.maciej.tcgenerator.model.UserInput
import com.procyk.maciej.tcgenerator.template.TemplateParser
import freemarker.template.Configuration
import freemarker.template.TemplateExceptionHandler
import java.io.File
import java.io.StringWriter

class FreemarkerTemplateParser(private val templateFile: File) : TemplateParser {

    override fun mergeWithTemplate(testCase: TestCase, userInput: UserInput): String {
        val dataModel = mapOf(
            "testCase" to testCase,
            "userInput" to userInput.value
        )
        return with(Configuration(Configuration.VERSION_2_3_30)) {
            setDirectoryForTemplateLoading(templateFile.absoluteFile.toPath().parent.toFile())
            defaultEncoding = Charsets.UTF_8.name()
            templateExceptionHandler = TemplateExceptionHandler.RETHROW_HANDLER
            logTemplateExceptions = false
            wrapUncheckedExceptions = true
            fallbackOnNullLoopVariable = false
            whitespaceStripping = false
            val template = getTemplate(templateFile.name)
            val outStream = StringWriter()
            template.process(dataModel, outStream)
            outStream.toString()
        }
    }
}
