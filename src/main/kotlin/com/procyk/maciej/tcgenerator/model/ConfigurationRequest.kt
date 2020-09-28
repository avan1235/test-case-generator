package com.procyk.maciej.tcgenerator.model

import com.procyk.maciej.tcgenerator.util.Valid

interface ConfigurationRequest {

    fun configure(): Valid?
}
