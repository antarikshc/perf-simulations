# ANR Simulations

## Available Simulations

### 1. Main Thread Sleep (DIRECT)

Blocks UI thread for 6 seconds using Thread.sleep()

- **ID**: `main_thread_sleep`
- **Duration**: 6 seconds
- **Recovery**: Automatic
- **Location**: `MainActivity.executeAnrSimulation()`
- **Requires Confirmation**: Yes

### 2. Infinite Loop Activity (ACTIVITY)

Launches separate activity with an infinite loop that can be triggered by user interaction

- **ID**: `infinite_loop_activity`
- **Duration**: Indefinite (until force closed)
- **Recovery**: Force close required
- **Location**: `InfiniteLoopAnrActivity.startInfiniteLoop()`
- **Requires Confirmation**: Yes

### 3. Bubble Sort on Main Thread (DIRECT)

Sorts a large array (100,000 elements) using bubble sort algorithm on the main thread, causing
CPU-bound ANR

- **ID**: `bubble_sort_main_thread`
- **Duration**: Device-dependent (typically 10-30 seconds)
- **Recovery**: Automatic (after sort completes)
- **Location**: `MainActivity.executeAnrSimulation()`
- **Requires Confirmation**: Yes

## Architecture

### Execution Types

- **DIRECT**: ANR code executes immediately in current context
- **ACTIVITY**: Launches separate activity for more complex ANR scenarios

### Data Structure

Simulations are defined using a sealed class hierarchy:

```kotlin
sealed class Simulation(
    open val id: String,
    open val name: String,
    open val description: String,
    open val requiresConfirmation: Boolean = true
) {
    abstract val type: SimulationType

    data class DirectSimulation(...) : Simulation(...)
    data class ActivitySimulation(...) : Simulation(...)
}
```

### Adding New Simulations

1. **Add constants to Simulations.kt**:

```kotlin
const val NEW_SIMULATION_ID = "new_simulation"
```

2. **Add to Simulations.getAllSimulations()**:

```kotlin
Simulation.DirectSimulation(
    id = NEW_SIMULATION_ID,
    name = "New Simulation",
    description = "Description of the ANR behavior",
    requiresConfirmation = true
)
```

3. **Handle execution in MainActivity.executeAnrSimulation()**:

```kotlin
when (simulation.type) {
    SimulationType.DIRECT -> {
        when (simulation.id) {
            Simulations.NEW_SIMULATION_ID -> {
                // Your ANR code here
            }
        }
    }
    SimulationType.ACTIVITY -> {
        when (simulation.id) {
            Simulations.NEW_SIMULATION_ID -> {
                startActivity(Intent(this, NewActivity::class.java))
            }
        }
    }
}
```

## Safety Notes

⚠️ **Use only in testing environments. Some ANRs require force-closing the app.**

- All simulations require user confirmation before execution
- DIRECT simulations execute immediately on the main thread
- ACTIVITY simulations launch separate activities that may need to be force-closed