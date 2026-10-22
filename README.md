# Vector Studio

Vector Studio is a native Android tablet app for vector design and DTF artwork creation. It is built with Kotlin and Jetpack Compose and optimized for 11-inch tablets.

## Features
- Native Compose UI designed for tablet landscape layouts
- Dark and light themes
- Vector editing canvas with draggable shapes
- Rectangle, circle, and star primitives
- Shape selection, fill and stroke editing, rotation, stroke width controls
- Layer list and inspector panel
- SVG export for design files

## Requirements
- Android Studio Ladybug or newer
- JDK 17
- Android SDK 34

## Build
```bash
./gradlew assembleDebug
```

## Run
Open the project in Android Studio and run the app on an emulator or physical tablet.

## Notes
This project is structured to compile as a native Android app and produce a debug APK for development and testing.

The export feature writes an SVG file into the app documents directory under `Vector Studio/`.

## App name
Vector Studio
