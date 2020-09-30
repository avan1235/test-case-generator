package com.procyk.maciej.tcgenerator.model

abstract class TestCaseProviderRequesterFactory<T : TestCaseProvider> {

    abstract fun createRequest(): TestCaseProviderRequester<T>

    fun getUniqueName(): String {
        val name = this.javaClass.simpleName
        val index = name
            .map { it.isUpperCase() }
            .mapIndexed { index, uppercase -> if (uppercase) index else -1 }
            .drop(1)
            .first { it > -1 }
        return name.substring(0 until index)
    }
}
