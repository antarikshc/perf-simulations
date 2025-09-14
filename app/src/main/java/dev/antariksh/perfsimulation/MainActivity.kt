package dev.antariksh.perfsimulation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dev.antariksh.perfsimulation.components.ConfirmationDialog
import dev.antariksh.perfsimulation.components.SimulationItem
import dev.antariksh.perfsimulation.ui.theme.PerfSimulationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PerfSimulationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PerfSimulationScreen(
                        onExecuteAnr = { simulation -> executeAnrSimulation(simulation) },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private fun executeAnrSimulation(simulation: Simulation) {
        when (simulation) {
            is Simulation.DirectSimulation -> simulation.perform(this)
            is Simulation.ActivitySimulation -> startActivity(
                Intent(
                    this,
                    simulation.activityClass.java
                )
            )
        }
    }
}

@Composable
fun PerfSimulationScreen(
    onExecuteAnr: (Simulation) -> Unit,
    modifier: Modifier = Modifier
) {
    var simulationToConfirm by remember { mutableStateOf<Simulation?>(null) }

    PerfSimulationList(
        onExecuteAnr = { simulation ->
            if (simulation.requiresConfirmation) {
                simulationToConfirm = simulation
            } else {
                onExecuteAnr(simulation)
            }
        },
        modifier = modifier
    )

    simulationToConfirm?.let { simulation ->
        ConfirmationDialog(
            simulation = simulation,
            onConfirm = {
                onExecuteAnr(simulation)
                simulationToConfirm = null
            },
            onDismiss = {
                simulationToConfirm = null
            }
        )
    }
}

@Composable
fun PerfSimulationList(
    onExecuteAnr: (Simulation) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(Simulations.getAllSimulations()) { simulation ->
            SimulationItem(
                simulation = simulation,
                onExecute = onExecuteAnr
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PerfSimulationScreenPreview() {
    PerfSimulationTheme {
        PerfSimulationScreen(onExecuteAnr = {})
    }
}