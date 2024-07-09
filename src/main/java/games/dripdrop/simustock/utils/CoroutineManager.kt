package games.dripdrop.simustock.utils

import kotlinx.coroutines.*

object CoroutineManager {
    private var mCoroutineScope = MainScope()
    private val mCoroutineMap: HashMap<String, Job?> = hashMapOf()

    fun runOnIOThread(tag: String, action: () -> Unit) {
        runCoroutine(tag) { action() }
    }

    fun runOnIOThreadWithDelay(tag: String, delayMillis: Long, action: () -> Unit) {
        if (delayMillis <= 0L) {
            PluginLogManager.w("delay time cannot be smaller than 0")
            runOnIOThread(tag, action)
            return
        }
        runCoroutine(tag) {
            delay(delayMillis)
            action()
        }
    }

    fun runOnIOThreadWithPeriod(tag: String, intervalMillis: Long, action: () -> Unit) {
        if (intervalMillis < 0L) {
            PluginLogManager.w("interval time cannot be smaller than 0")
        }
        runCoroutine(tag) {
            while (true == mCoroutineMap[tag]?.isActive) {
                action()
                delay(if (intervalMillis <= 0L) 1000L else intervalMillis)
            }
        }
    }

    fun cancelJob(tag: String) {
        PluginLogManager.i("cancel an async task [$tag]")
        mCoroutineMap[tag]?.cancel()
    }

    fun cancelCoroutine() {
        mCoroutineMap.values.forEach { it?.cancel() }
        mCoroutineMap.clear()
        mCoroutineScope.cancel()
    }

    private fun runCoroutine(
        tag: String,
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        action: suspend () -> Unit
    ) {
        PluginLogManager.i("run an async task [$tag], dispatcher is $dispatcher")
        cancelJob(tag)
        if (!mCoroutineScope.isActive) {
            mCoroutineScope = MainScope()
        }
        mCoroutineMap[tag] = mCoroutineScope.launch(dispatcher) {
            supervisorScope { action() }
        }
    }
}