package dev.antariksh.anrsimulation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.antariksh.anrsimulation.ui.theme.ANRSimulationTheme

class InfiniteLoopAnrActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ANRSimulationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    InfiniteLoopContent(
                        onStartAnr = { startInfiniteLoop() },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    private fun startInfiniteLoop() {
        while (true) {
            // This will cause an ANR by creating an infinite loop on the main thread
            // The loop will continue indefinitely, blocking the UI thread
        }
    }
}

@Composable
fun InfiniteLoopContent(
    onStartAnr: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Infinite Loop ANR",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "This will create an infinite loop on the main thread, causing an ANR.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Button(onClick = onStartAnr) {
            Text("Start Infinite Loop")
        }
    }
}