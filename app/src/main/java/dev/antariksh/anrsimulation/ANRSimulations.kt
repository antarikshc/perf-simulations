package dev.antariksh.anrsimulation

object ANRSimulations {
    fun getAllSimulations(): List<ANRSimulation> = listOf(
        ANRSimulation.DirectSimulation(
            id = "main_thread_sleep",
            name = "Main Thread Sleep",
            description = "Blocks UI thread for 10 seconds using Thread.sleep()",
            requiresConfirmation = true
        ),
        ANRSimulation.ActivitySimulation(
            id = "infinite_loop_activity",
            name = "Infinite Loop Activity",
            description = "Launches separate activity with an infinite loop",
            requiresConfirmation = true
        )
    )
}