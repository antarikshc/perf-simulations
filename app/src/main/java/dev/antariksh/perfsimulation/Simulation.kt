package dev.antariksh.perfsimulation

sealed class Simulation(
    open val id: String,
    open val name: String,
    open val description: String,
    open val requiresConfirmation: Boolean = true
) {
    abstract val type: SimulationType

    data class DirectSimulation(
        override val id: String,
        override val name: String,
        override val description: String,
        override val requiresConfirmation: Boolean = true
    ) : Simulation(id, name, description, requiresConfirmation) {
        override val type: SimulationType = SimulationType.DIRECT
    }

    data class ActivitySimulation(
        override val id: String,
        override val name: String,
        override val description: String,
        override val requiresConfirmation: Boolean = true
    ) : Simulation(id, name, description, requiresConfirmation) {
        override val type: SimulationType = SimulationType.ACTIVITY
    }
}
