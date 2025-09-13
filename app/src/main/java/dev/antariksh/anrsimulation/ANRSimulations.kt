package dev.antariksh.anrsimulation

object ANRSimulations {
    // ANR Simulation ID Constants
    const val MAIN_THREAD_SLEEP_ID = "main_thread_sleep"
    const val INFINITE_LOOP_ACTIVITY_ID = "infinite_loop_activity"

    fun getAllSimulations(): List<ANRSimulation> = listOf(
        ANRSimulation.DirectSimulation(
            id = MAIN_THREAD_SLEEP_ID,
            name = "Main Thread Sleep",
            description = "Blocks UI thread for 10 seconds using Thread.sleep()",
            requiresConfirmation = true
        ),
        ANRSimulation.ActivitySimulation(
            id = INFINITE_LOOP_ACTIVITY_ID,
            name = "Infinite Loop Activity",
            description = "Launches separate activity with an infinite loop",
            requiresConfirmation = true
        )
    )
}