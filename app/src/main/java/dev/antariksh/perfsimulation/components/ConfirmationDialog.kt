package dev.antariksh.perfsimulation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import dev.antariksh.perfsimulation.Simulation

@Composable
fun ConfirmationDialog(
    simulation: Simulation,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Confirm Simulation")
        },
        text = {
            Text("Are you sure you want to execute '${simulation.name}'? This might hang your device.")
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("Execute")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}