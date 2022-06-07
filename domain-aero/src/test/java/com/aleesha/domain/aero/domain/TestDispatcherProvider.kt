package com.aleesha.domain.aero.domain

import com.aleesha.core.coroutine.SuspendDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher

class TestDispatcherProvider : SuspendDispatcher {
    private val testDispatcher = UnconfinedTestDispatcher()
    override fun main() = testDispatcher
    override fun io() = testDispatcher
    override fun compute() = testDispatcher
}
