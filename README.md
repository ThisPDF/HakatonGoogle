# Wear OS Smart Home

A Wear OS application that tracks location and sends it to a companion Android app for smart home automation.

## Project Structure

- **wear**: Wear OS application that tracks location
- **mobile**: Android mobile application that receives location data and controls smart home devices
- **shared**: Shared code between Wear OS and mobile app

## Setup Instructions

1. Clone the repository
2. Open the project in Android Studio
3. Create a `local.properties` file with your SDK path (use the template as a reference)
4. Sync the project with Gradle files
5. Build and run the application

## Requirements

- Android Studio Hedgehog (2023.1.1) or newer
- Gradle 8.4 or newer
- Android SDK 34
- Kotlin 1.9.0 or newer

## Features

- Location tracking on Wear OS
- Data synchronization between Wear OS and mobile app
- Smart home device control based on location
- Automatic adjustment of home settings when arriving home
\`\`\`

Let's add a .idea directory with some basic configuration:

```xml file=".idea/compiler.xml"
<?xml version="1.0" encoding="UTF-8"?>
<project version="4">
  <component name="CompilerConfiguration">
    <bytecodeTargetLevel target="17" />
  </component>
</project>
