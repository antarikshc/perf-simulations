package dev.antariksh.perfsimulation

object Simulations {
    // ANR Simulation ID Constants
    const val MAIN_THREAD_SLEEP_ID = "main_thread_sleep"
    const val INFINITE_LOOP_ACTIVITY_ID = "infinite_loop_activity"
    const val BUBBLE_SORT_MAIN_THREAD_ID = "bubble_sort_main_thread"

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
                val size = 100_000
                val arr = IntArray(size) { kotlin.random.Random.nextInt(0, size) }
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
        Simulation.ActivitySimulation(
            id = INFINITE_LOOP_ACTIVITY_ID,
            name = "Infinite Loop Activity",
            description = "Launches separate activity with an infinite loop on main thread",
            requiresConfirmation = true,
            activityClass = InfiniteLoopAnrActivity::class
        )
    )
}
