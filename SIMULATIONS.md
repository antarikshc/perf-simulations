# Perf Simulations

This document describes the available ANR simulation scenarios with their implementation details,
including the core code that causes the ANR.

## Available Simulations

### 1. Main Thread Sleep (DIRECT)

Blocks UI thread for 6 seconds using Thread.sleep()
- **ID**: `main_thread_sleep`
- **Duration**: 6 seconds
- **Recovery**: Automatic

```kotlin
perform = { Thread.sleep(6000) }
```

### 2. Infinite Loop Activity (ACTIVITY)

Launches separate activity with an infinite loop that can be triggered by user interaction
- **ID**: `infinite_loop_activity`
- **Duration**: Indefinite (until force closed)
- **Recovery**: Force close required
- **Location**: `InfiniteLoopAnrActivity` (launched via activityClass)

```kotlin
private fun startInfiniteLoop() {
    while (true) {
        // This will cause an ANR by creating an infinite loop on the main thread
        // The loop will continue indefinitely, blocking the UI thread
    }
}
```

### 3. Bubble Sort on Main Thread (DIRECT)

Sorts a large array (100,000 elements) using bubble sort algorithm on the main thread, causing
CPU-bound ANR
- **ID**: `bubble_sort_main_thread`
- **Duration**: Device-dependent (typically 5-10 seconds)
- **Recovery**: Automatic (after sort completes)

**Code snippet:**

```kotlin
perform = {
    val size = 100_000
    val arr = IntArray(size) { kotlin.random.Random.nextInt(0, size) }
    for (i in 0 until size - 1) {
        for (j in 0 until size - i - 1) {
            if (arr[j] > arr[j + 1]) {
                val tmp = arr[j]
                arr[j] = arr[j + 1]
                arr[j + 1] = tmp
            }
        }
    }
}
```

### 4. Binder IPC Overload (DIRECT)

Makes 1000 sequential binder calls to Settings.System.getInt() with 10ms delays between each call,
causing cumulative IPC delays that exceed the input dispatch timeout (5 seconds)

- **ID**: `binder_ipc_overload`
- **Duration**: ~10 seconds (1000 calls × 10ms each)
- **Recovery**: Automatic (after all calls complete)

**Code snippet:**

```kotlin
perform = { context ->
    // 1000 sequential binder calls with added delays
    for (i in 0..999) {
        try {
            Settings.System.getInt(context.contentResolver, Settings.System.SCREEN_BRIGHTNESS)
            SystemClock.sleep(10) // 10ms per iteration = ~10 seconds total
        } catch (e: Settings.SettingNotFoundException) {
            e.printStackTrace()
        }
    }
}
```

## Architecture

### Execution Types

- **DIRECT**: ANR code executes via `perform` lambda stored in `Simulation.DirectSimulation`
- **ACTIVITY**: Launches activity specified by `activityClass` in `Simulation.ActivitySimulation`

### Data Structure

Simulations are defined using a sealed class hierarchy that includes both metadata and execution
behavior:

```kotlin
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
        val perform: (Context) -> Unit  // Execution behavior co-located with metadata
    ) : Simulation(id, name, description, requiresConfirmation) {
        override val type: SimulationType = SimulationType.DIRECT
    }

    data class ActivitySimulation(
        override val id: String,
        override val name: String,
        override val description: String,
        override val requiresConfirmation: Boolean = true,
        val activityClass: KClass<out Activity>  // Activity class for execution
    ) : Simulation(id, name, description, requiresConfirmation) {
        override val type: SimulationType = SimulationType.ACTIVITY
    }
}
```

**Note**: No changes needed in `MainActivity.executeAnrSimulation()` - execution is handled
automatically using sealed-type dispatch.

## Safety Notes

⚠️ **Use only in testing environments. Some ANRs require force-closing the app.**

- All simulations require user confirmation before execution
- DIRECT simulations execute immediately on the main thread
- ACTIVITY simulations launch separate activities that may need to be force-closed