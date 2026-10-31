# RootMate 🛠️

**RootMate** is a native Android utility designed for developers and enthusiasts preparing their devices for rooting. It prioritizes safety, education, and offline accessibility, providing a structured "flight checklist" before users proceed with risky system modifications.

## 🚀 Features

- **Terminal Audit Dashboard**: Real-time system data overview (Manufacturer, Model, Android Version, API Level, Build Number).
- **Deep System Audit**: Detailed technical specs including Kernel version, SoC, Board, and Bootloader info.
- **Developer Environment Checker**: Real-time monitoring of Developer Options, USB Debugging, and OEM Unlocking status.
- **Preparation Checklists**: persistent, category-based checklists for Backups and Environment Setup.
- **Educational Guides**: Offline-first tutorials covering ADB Setup, OEM Unlocking, Drivers, and SafetyNet.
- **Exportable Reports**: Generate and share readiness reports as text for forum troubleshooting or personal reference.
- **Cyberpunk Utilitarian UI**: High-readiness dark theme with neon accents and monospace typography.

## 🛡️ Safety Philosophy

RootMate is **strictly educational**. 
- ❌ No automated rooting or flashing scripts.
- ❌ No risky system modifications.
- ❌ No cloud data uploads (100% Offline).
- ✅ Dynamic readiness scoring based on system properties.
- ✅ Clear "UNKNOWN" state handling for restricted APIs.

## 🛠️ Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose (Material 3)
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: Room (Offline Persistence)
- **Navigation**: Type-safe Navigation Compose
- **Typography**: JetBrains Mono (Bundled locally)

## 📦 Building from Source

1. Clone the repository.
2. Open in Android Studio (Jellyfish or newer recommended).
3. Ensure you have JDK 17 configured.
4. Build the project using the Gradle wrapper:
   ```bash
   ./gradlew assembleDebug
   ```

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

---
*Disclaimer: RootMate is a preparation tool. Rooting your device carries inherent risks including data loss and hardware damage. Use the information provided at your own risk.*
