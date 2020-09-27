package com.procyk.maciej.tcgenerator.util

fun String.notBlank(): String? {
    if (this.isBlank()) {
        return null
    }
    return this
}
