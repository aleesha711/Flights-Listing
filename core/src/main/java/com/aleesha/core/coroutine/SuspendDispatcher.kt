package com.aleesha.core.coroutine

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Abstraction which acts as delegation to real or test dispatchers.
 */
interface SuspendDispatcher {

    /**
     * Main Thread
     */
    fun main(): CoroutineDispatcher

    /**
     * IO Related Task (here are more Threads in the Pool)
     */
    fun io(): CoroutineDispatcher

    /**
     * CPU Heavy Tasks. Amount of Threads in this Pool is limited to CPU-Cores
     */
    fun compute(): CoroutineDispatcher
}
