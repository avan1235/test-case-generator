package com.procyk.maciej.tcgenerator.util

object Valid

fun Boolean.valid(): Valid? = if (this) Valid else null