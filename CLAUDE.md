# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this
repository.

## Project Overview

This is an Android application called "ANR Simulation" designed to simulate Application Not
Responding (ANR) scenarios, likely for testing with Perfetto tracing and MCP (Model Context
Protocol) integration.

- **Package**: `dev.antariksh.anrsimulation`
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Min SDK**: 24, Target SDK: 36
- **Build System**: Gradle with Kotlin DSL

## Development Commands

### Build and Run

```bash
# Build the project
./gradlew build

# Build debug APK
./gradlew assembleDebug

# Install debug APK to connected device
./gradlew installDebug

# Clean build
./gradlew clean
```

### Testing

```bash
# Run unit tests
./gradlew testDebugUnitTest

# Run instrumented tests (requires connected device/emulator)
./gradlew connectedDebugAndroidTest

# Run all tests
./gradlew test
```

### Code Quality

```bash
# Check for Kotlin code style issues (if configured)
./gradlew ktlintCheck

# Apply Kotlin code formatting (if configured)
./gradlew ktlintFormat
```

## Architecture

### Package Structure

- `app/src/main/java/dev/antariksh/anrsimulation/`
    - `MainActivity.kt` - Main activity with Compose UI setup
    - `ui/theme/` - Compose theme configuration (Color.kt, Type.kt, Theme.kt)

### Key Technologies

- **Jetpack Compose**: Modern Android UI toolkit for building native UI
- **Material 3**: Latest Material Design components
- **Edge-to-Edge**: Modern Android UI that extends to system bars

### Dependencies Management

- Uses Gradle Version Catalogs (`gradle/libs.versions.toml`) for centralized dependency management
- Core dependencies include Compose BOM, Activity Compose, Material 3, and testing libraries

### Build Configuration

- Java 11 compatibility
- Compose compiler enabled
- Standard Android build types (debug/release)
- ProGuard configuration for release builds