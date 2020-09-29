package com.procyk.maciej.tcgenerator.util

import java.io.File

fun validateFilePath(path: String): File? {
    val file = File(path)
    if (!file.exists() || file.isDirectory) {
        return null
    }
    return file
}
