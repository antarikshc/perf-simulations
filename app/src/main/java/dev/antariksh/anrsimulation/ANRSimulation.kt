package dev.antariksh.anrsimulation

sealed class ANRSimulation(
    open val id: String,
    open val name: String,
    open val description: String,
    open val requiresConfirmation: Boolean = true
) {
    abstract val type: ANRType

    data class DirectSimulation(
        override val id: String,
        override val name: String,
        override val description: String,
        override val requiresConfirmation: Boolean = true
    ) : ANRSimulation(id, name, description, requiresConfirmation) {
        override val type: ANRType = ANRType.DIRECT
    }

    data class ActivitySimulation(
        override val id: String,
        override val name: String,
        override val description: String,
        override val requiresConfirmation: Boolean = true
    ) : ANRSimulation(id, name, description, requiresConfirmation) {
        override val type: ANRType = ANRType.ACTIVITY
    }
}