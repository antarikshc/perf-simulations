# Perf Simulation App

A modern Android application designed to simulate Application Not Responding (ANR) scenarios for
testing with performance analysis tools like Perfetto tracing and other debugging utilities.

## Overview

This app provides a controlled environment to trigger different types of ANRs, helping developers
understand ANR patterns, test monitoring tools, and analyze app performance under stress conditions.

## Features

- **Multiple ANR Types**: Direct execution and activity-based simulations
- **User-Friendly Interface**: Material 3 design with Jetpack Compose
- **Confirmation Dialogs**: Optional safety confirmations before triggering ANRs
- **Scalable Architecture**: Easy to add new ANR simulation scenarios
- **Perfetto Integration Ready**: Designed for use with performance tracing tools

## Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Design System**: Material 3
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 36
- **Build System**: Gradle with Kotlin DSL

## Project Structure

```
app/src/main/java/dev/antariksh/perfsimulation/
├── SimulationType.kt                          # Enum defining simulation execution types
├── Simulation.kt                    # Data class for simulation metadata
├── Simulations.kt                   # Sealed class containing all simulation definitions
├── MainActivity.kt                     # Main screen with simulation list and execution logic
├── InfiniteLoopAnrActivity.kt         # Example activity-based ANR simulation
├── components/
│   ├── PerfSimulationItem.kt           # List item component for each simulation
│   ├── SimulationItem.kt               # List item component for each simulation
│   └── ConfirmationDialog.kt          # Optional confirmation dialog component
└── ui/theme/                          # Material 3 theme configuration
    ├── Color.kt
    ├── Theme.kt
    └── Type.kt
```

## Architecture

### Data Layer

- **SimulationType**: Enum with `DIRECT` and `ACTIVITY` execution types
- **Simulation**: Data class containing simulation metadata (id, name, description, type,
  confirmation requirement)
- **Simulations**: Sealed class with companion object storing all available simulations

### UI Layer

- **MainActivity**: Hosts the main simulation list and handles execution logic
- **PerfSimulationScreen**: Manages state and confirmation dialogs
- **PerfSimulationList**: LazyColumn displaying all available simulations
- **PerfSimulationItem**: Individual list item with execute button

### Execution Types

1. **DIRECT**: ANR code executes immediately in current context
2. **ACTIVITY**: Launches separate activity for more complex ANR scenarios

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

1. **Add to Simulations.kt**:
   ```kotlin
   Simulation.DirectSimulation(
       id = "new_anr_id",
       name = "New ANR Type",
       description = "Description of the ANR behavior",
       type = SimulationType.DIRECT, // or SimulationType.ACTIVITY
       requiresConfirmation = true
   )
   ```

2. **Handle execution in MainActivity**:
   ```kotlin
   // For DIRECT type
   SimulationType.DIRECT -> {
       when (simulation.id) {
           "new_anr_id" -> {
               // Your ANR code here
           }
       }
   }

   // For ACTIVITY type
   SimulationType.ACTIVITY -> {
       when (simulation.id) {
           "new_anr_id" -> {
               startActivity(Intent(this, NewAnrActivity::class.java))
           }
       }
   }
   ```

3. **For ACTIVITY type, create new activity**:
    - Create new Activity class
    - Add to AndroidManifest.xml
    - Implement ANR logic in the activity

## Safety Notes

⚠️ **Warning**: This app is designed to intentionally freeze and become unresponsive. Use only in
controlled testing environments.

- ANRs will make the app unresponsive
- Some simulations may require force-closing the app
- Recommended for use with debugging and analysis tools
- Not intended for production environments

## Contributing

When adding new ANR simulations:

1. Follow the existing pattern in `ANRSimulations.kt`
2. Add appropriate handling in `MainActivity.executeAnrSimulation()`
3. Update `SIMULATIONS.md` with details about the new simulation
4. Test the simulation thoroughly

## License

This project is created for educational and testing purposes.