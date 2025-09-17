package dev.antariksh.perfsimulation

import android.os.SystemClock
import android.provider.Settings
import kotlin.random.Random

object Simulations {
    // ANR Simulation ID Constants
    const val MAIN_THREAD_SLEEP_ID = "main_thread_sleep"
    const val INFINITE_LOOP_ACTIVITY_ID = "infinite_loop_activity"
    const val BUBBLE_SORT_MAIN_THREAD_ID = "bubble_sort_main_thread"
    const val BINDER_IPC_OVERLOAD_ID = "binder_ipc_overload"
    const val JOIN_WORKER_ON_MAIN_ID = "join_worker_on_main"

    fun getAllSimulations(): List<Simulation> = listOf(
        Simulation.DirectSimulation(
            id = MAIN_THREAD_SLEEP_ID,
            name = "Main Thread Sleep",
            description = "Blocks UI thread for ~6 seconds using Thread.sleep()",
            requiresConfirmation = true,
            perform = { Thread.sleep(6000) }
        ),
        Simulation.DirectSimulation(
            id = BUBBLE_SORT_MAIN_THREAD_ID,
            name = "Bubble Sort on Large Array",
            description = "Sorts a large array with bubble sort on the UI thread",
            requiresConfirmation = true,
            perform = {
                // 100K will cause ANR
                // ~50K will be a long running task, good for testing ANR-like scenarios
                val size = 100_000
                val arr = IntArray(size) { Random.nextInt(0, size) }
                for (i in 0 until size - 1) {
                    for (j in 0 until size - i - 1) {
                        if (arr[j] > arr[j + 1]) {
                            val tmp = arr[j]
                            arr[j] = arr[j + 1]
                            arr[j + 1] = tmp
                        }
                    }
                }
            }
        ),
        Simulation.DirectSimulation(
            id = BINDER_IPC_OVERLOAD_ID,
            name = "Binder IPC Overload",
            description = "1000 sequential binder calls with delays to exceed input dispatch timeout",
            requiresConfirmation = true,
            perform = { context ->
                // 1000 sequential binder calls with added delays
                repeat(1000) {
                    // 1000 will cause ANR
                    // ~400 will be a long running task, good for testing ANR-like scenarios
                    try {
                        Settings.System.getInt(
                            context.contentResolver,
                            Settings.System.SCREEN_BRIGHTNESS
                        )
                        SystemClock.sleep(10) // 10ms per iteration = ~10 seconds total
                    } catch (e: Settings.SettingNotFoundException) {
                        e.printStackTrace()
                    }
                }
            }
        ),
        Simulation.DirectSimulation(
            id = JOIN_WORKER_ON_MAIN_ID,
            name = "Join Worker on Main",
            description = "UI thread blocks with Thread.join() waiting for a worker (~6s)",
            requiresConfirmation = true,
            perform = { _ ->
                val worker = Thread { Thread.sleep(6000L) } // 6s to reliably trigger ANR
                worker.start()
                worker.join() // BAD: blocks main thread until worker completes
            }
        ),
        Simulation.ActivitySimulation(
            id = INFINITE_LOOP_ACTIVITY_ID,
            name = "Infinite Loop Activity",
            description = "Launches separate activity with an infinite loop on main thread",
            requiresConfirmation = true,
            activityClass = InfiniteLoopAnrActivity::class
        )
    )
}
