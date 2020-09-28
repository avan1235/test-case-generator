package com.procyk.maciej.tcgenerator.util

@Throws(ValidationException::class)
fun <T> T?.throwOnNull(message: String? = null): T {
    if (this == null) {
        throw ValidationException(message)
    }
    return this
}

class ValidationException(message: String?) : Exception(message ?: "No info")

object Valid
