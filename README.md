# Perf Simulation App

A modern Android application designed to simulate Application Not Responding (ANR) scenarios for
testing with performance analysis tools like Perfetto tracing and other debugging utilities.

## Overview

This app provides a controlled environment to trigger different types of ANRs, helping developers
understand ANR patterns, test monitoring tools, and analyze app performance under stress conditions.

## SDK

- **UI Framework**: Jetpack Compose
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 36
- **Build System**: Gradle with Kotlin DSL

## Project Structure

```
app/src/main/java/dev/antariksh/perfsimulation/
├── SimulationType.kt                     # Enum defining simulation execution types
├── Simulation.kt                         # Sealed class for simulation data structure
├── Simulations.kt                        # Object containing all simulation definitions
├── MainActivity.kt                       # Main screen with simulation list and execution logic
├── InfiniteLoopAnrActivity.kt            # Activity-based ANR simulation with infinite loop
├── components/
│   ├── SimulationItem.kt                 # List item component for each simulation
│   ├── PerfSimulationItem.kt             # Duplicate component (consider removing)
│   └── ConfirmationDialog.kt             # Confirmation dialog for ANR simulations
└── ui/theme/                             # Material 3 theme configuration
    ├── Color.kt
    ├── Theme.kt
    └── Type.kt
```

## Architecture

### Data Layer

- **SimulationType**: Enum with `DIRECT` and `ACTIVITY` execution types
- **Simulation**: Sealed class hierarchy with `DirectSimulation` and `ActivitySimulation` subclasses
  containing simulation metadata (id, name, description, confirmation requirement) **and behavior**
    - `DirectSimulation`: Contains `perform: (Context) -> Unit` lambda for direct ANR execution
    - `ActivitySimulation`: Contains `activityClass: KClass<out Activity>` for activity-based ANRs
- **Simulations**: Object with constants and `getAllSimulations()` function returning available
  simulations with their execution behavior co-located

### UI Layer

- **MainActivity**: Hosts the main screen with `PerfSimulationScreen` composable and handles simple
  ANR execution dispatch using sealed-type pattern
- **PerfSimulationScreen**: Manages state for confirmation dialogs and orchestrates simulation
  execution
- **PerfSimulationList**: LazyColumn displaying all available simulations
- **SimulationItem**: Individual list item component with execute button
- **ConfirmationDialog**: Alert dialog for confirming ANR simulation execution

### Execution Types

1. **DIRECT**: ANR code executes via lambda stored in `Simulation.DirectSimulation.perform`
2. **ACTIVITY**: Launches activity specified in `Simulation.ActivitySimulation.activityClass`

## Development Commands

### Building

```bash
# Build the project
./gradlew build

# Build debug APK
./gradlew assembleDebug

# Install debug APK to connected device
./gradlew installDebug
```

### Testing

```bash
# Run unit tests
./gradlew testDebugUnitTest

# Run instrumented tests (requires device/emulator)
./gradlew connectedDebugAndroidTest
```

## Adding New ANR Simulations

To add a new ANR simulation:

1. **Add constants to Simulations.kt**:
   ```kotlin
   const val NEW_ANR_ID = "new_anr_id"
   ```

2. **Add a direct executable ANR** (DIRECT type):
   ```kotlin
   Simulation.DirectSimulation(
       id = NEW_ANR_ID,
       name = "New ANR Type",
       description = "Description of the ANR behavior",
       requiresConfirmation = true,
       perform = { context ->
           // Your ANR code here (e.g., Thread.sleep(), infinite loop, heavy computation)
           // This lambda executes on the main thread to intentionally cause ANR
       }
   )
   ```

3. **Or add an activity that contains ANR** (ACTIVITY type):
   ```kotlin
   Simulation.ActivitySimulation(
       id = NEW_ANR_ID,
       name = "New ANR Activity",
       description = "Description of the ANR behavior",
       requiresConfirmation = true,
       activityClass = NewAnrActivity::class // Create new Activity eg: InfiniteLoopAnrActivity.kt
   )
   ```

**Note**: No changes needed in `MainActivity.executeAnrSimulation()` - the execution logic is
automatically handled by the sealed-type dispatch pattern.

## Safety Notes

⚠️ **Warning**: This app is designed to intentionally freeze and become unresponsive. Use only in
controlled testing environments.

- ANRs will make the app unresponsive
- Some simulations may require force-closing the app
- Recommended for use with debugging and analysis tools
- Not intended for production environments

## Contributing

When adding new ANR simulations:

1. Follow the existing pattern in `Simulations.kt`
2. Include execution logic in the `perform` lambda (for DIRECT) or specify `activityClass` (for
   ACTIVITY)
3. Update `SIMULATIONS.md` with details about the new simulation
4. Test the simulation thoroughly in a controlled environment
5. No changes needed in `MainActivity.executeAnrSimulation()` - execution is handled automatically
6. Consider removing duplicate components to maintain clean codebase

## License

This project is created for educational and testing purposes.
