package dev.antariksh.anrsimulation

data class ANRSimulation(
    val id: String,
    val name: String,
    val description: String,
    val type: ANRType,
    val requiresConfirmation: Boolean = true
)