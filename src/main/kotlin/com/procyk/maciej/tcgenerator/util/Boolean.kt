package com.procyk.maciej.tcgenerator.util

fun Boolean.validate(): Valid? = if (this) Valid else null
