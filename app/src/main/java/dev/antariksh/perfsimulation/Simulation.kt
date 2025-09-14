package dev.antariksh.perfsimulation

import android.app.Activity
import android.content.Context
import kotlin.reflect.KClass

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
        override val requiresConfirmation: Boolean = true,
        val perform: (Context) -> Unit
    ) : Simulation(id, name, description, requiresConfirmation) {
        override val type: SimulationType = SimulationType.DIRECT
    }

    data class ActivitySimulation(
        override val id: String,
        override val name: String,
        override val description: String,
        override val requiresConfirmation: Boolean = true,
        val activityClass: KClass<out Activity>
    ) : Simulation(id, name, description, requiresConfirmation) {
        override val type: SimulationType = SimulationType.ACTIVITY
    }
}
