package dev.antariksh.anrsimulation

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
import dev.antariksh.anrsimulation.components.ConfirmationDialog
import dev.antariksh.anrsimulation.components.SimulationItem
import dev.antariksh.anrsimulation.ui.theme.ANRSimulationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ANRSimulationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ANRSimulationScreen(
                        onExecuteAnr = { simulation -> executeAnrSimulation(simulation) },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private fun executeAnrSimulation(simulation: Simulation) {
        when (simulation.type) {
            SimulationType.DIRECT -> {
                when (simulation.id) {
                    Simulations.MAIN_THREAD_SLEEP_ID -> {
                        // Block the main thread for 6 seconds
                        Thread.sleep(6000)
                    }
                }
            }

            SimulationType.ACTIVITY -> {
                when (simulation.id) {
                    Simulations.INFINITE_LOOP_ACTIVITY_ID -> {
                        startActivity(Intent(this, InfiniteLoopAnrActivity::class.java))
                    }
                }
            }
        }
    }
}

@Composable
fun ANRSimulationScreen(
    onExecuteAnr: (Simulation) -> Unit,
    modifier: Modifier = Modifier
) {
    var simulationToConfirm by remember { mutableStateOf<Simulation?>(null) }

    ANRSimulationList(
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
fun ANRSimulationList(
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
fun ANRSimulationScreenPreview() {
    ANRSimulationTheme {
        ANRSimulationScreen(onExecuteAnr = {})
    }
}