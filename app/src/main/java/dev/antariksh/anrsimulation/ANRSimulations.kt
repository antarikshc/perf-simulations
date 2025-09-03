package dev.antariksh.anrsimulation

sealed class ANRSimulations {
    companion object {
        fun getAllSimulations(): List<ANRSimulation> = listOf(
            ANRSimulation(
                id = "main_thread_sleep",
                name = "Main Thread Sleep",
                description = "Blocks UI thread for 10 seconds using Thread.sleep()",
                type = ANRType.DIRECT,
                requiresConfirmation = true
            ),
            ANRSimulation(
                id = "infinite_loop_activity",
                name = "Infinite Loop Activity",
                description = "Launches separate activity with an infinite loop",
                type = ANRType.ACTIVITY,
                requiresConfirmation = true
            )
        )
    }
}