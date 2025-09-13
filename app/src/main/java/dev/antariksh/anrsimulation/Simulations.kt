package dev.antariksh.anrsimulation

object Simulations {
    // ANR Simulation ID Constants
    const val MAIN_THREAD_SLEEP_ID = "main_thread_sleep"
    const val INFINITE_LOOP_ACTIVITY_ID = "infinite_loop_activity"

    fun getAllSimulations(): List<Simulation> = listOf(
        Simulation.DirectSimulation(
            id = MAIN_THREAD_SLEEP_ID,
            name = "Main Thread Sleep",
            description = "Blocks UI thread for 10 seconds using Thread.sleep()",
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
