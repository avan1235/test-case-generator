package com.procyk.maciej.tcgenerator.util

fun Boolean.valid(): Valid? = if (this) Valid else null

object Valid
