package com.aleesha.core.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * This are the Dispatchers which need to use within Production code.
 * Do not use within tests as it will lead to flaky tests.
 * There is a dedicated TestDispatcher you can use for your Tests.
 */
class AppCoroutineDispatchers : SuspendDispatcher {

    override fun main(): CoroutineDispatcher = Dispatchers.Main
    override fun io(): CoroutineDispatcher = Dispatchers.IO
    override fun compute(): CoroutineDispatcher = Dispatchers.Default
}
