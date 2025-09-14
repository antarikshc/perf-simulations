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
            description = "Blocks UI thread for 10 seconds using Thread.sleep()",
            requiresConfirmation = true
        ),
        Simulation.DirectSimulation(
            id = BUBBLE_SORT_MAIN_THREAD_ID,
            name = "Bubble Sort on Large Array",
            description = "Sorts a large array with bubble sort on the UI thread to trigger an ANR",
            requiresConfirmation = true
        ),
        Simulation.ActivitySimulation(
            id = INFINITE_LOOP_ACTIVITY_ID,
            name = "Infinite Loop Activity",
            description = "Launches separate activity with an infinite loop",
            requiresConfirmation = true
        )
    )
}
