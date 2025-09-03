# ANR Simulations

## Available Simulations

### 1. Main Thread Sleep (DIRECT)

Blocks UI thread for 10 seconds using Thread.sleep()

- **ID**: `main_thread_sleep`
- **Duration**: 10 seconds
- **Recovery**: Automatic
- **Location**: `MainActivity.executeAnrSimulation()`

### 2. Infinite Loop Activity (ACTIVITY)

Launches separate activity with an infinite loop

- **ID**: `infinite_loop_activity`
- **Duration**: Indefinite
- **Recovery**: Force close required
- **Location**: `InfiniteLoopAnrActivity.startInfiniteLoop()`

## Architecture

### Execution Types

- **DIRECT**: Executes immediately in current context
- **ACTIVITY**: Launches separate activity for ANR

### Adding New Simulations

1. **Add to ANRSimulations.kt**:

```kotlin
ANRSimulation(
    id = "new_id",
    name = "New ANR",
    description = "Description",
    type = ANRType.DIRECT
)
```

2. **Handle in MainActivity.kt**:

```kotlin
when (simulation.id) {
    "new_id" -> { /* ANR code */
    }
}
```

## Safety Notes

⚠️ **Use only in testing environments. Some ANRs require force-closing the app.**