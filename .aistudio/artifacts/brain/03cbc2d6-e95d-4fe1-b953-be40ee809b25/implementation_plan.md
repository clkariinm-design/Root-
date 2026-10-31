# RootMate Implementation Plan (Revised)

RootMate is a professional-grade, native Android utility designed to help users safely prepare their devices for rooting. It provides an offline-first experience with a "Cyberpunk Utilitarian" aesthetic, focusing on technical data, safety checklists, and educational tutorials.

## User Review & Critical Decisions

> [!IMPORTANT]
> This project prioritizes **trust and safety**. No automated rooting scripts or risky system modifications will be implemented. All guidance is educational and manual.

- **Confirmed Aesthetic**: Cyberpunk Utilitarian. Dark theme with neon accents (Cyan/Green), using monospace typography (JetBrains Mono) for technical data.
- **Handling Ambiguity**: The app will implement "UNKNOWN" states for any system information that cannot be reliably retrieved. It will never show a "Failure" state if the data is simply unavailable.
- **Safety Strategy**: 100% Offline. All tutorials and checklists are local. Rooting difficulty prediction will NOT rely solely on security patch levels (used for context only).
- **Developer Readiness**: Includes a real-time checker for Developer Options, USB Debugging, and OEM Unlocking status (where accessible via standard APIs).

## 1. Overview & Core Concept

- **What It Does**: RootMate acts as a "flight checklist" and technical dashboard for rooting. It audits the device environment, verifies developer settings, and guides the user through essential backup and preparation steps.
- **Target Audience**: Android enthusiasts and developers who value a structured, safety-first preparation workflow.
- **Key Value**: Prevents data loss and device issues by mandating a "Backup First" philosophy and ensuring all developer tools are correctly configured before the user proceeds with external rooting tools.

## 2. User Experience & Visual Design

### Screen Map
1.  **Splash / Startup Screen**: Branded entry with the RootMate terminal logo and initialization checks.
2.  **Main Dashboard**: High-level status overview showing system health, backup progress, and quick actions.
3.  **Device Audit Screen**: Detailed technical specifications (SoC, Kernel, API, Partitions). Uses monospace typography.
4.  **Developer Environment Screen**: Real-time status of Dev Options, USB Debugging, and OEM Unlocking.
5.  **Backup Checklist Screen**: Interactive task list for data safety (Photos, Contacts, App Data).
6.  **Tutorials Screen**: Categorized list of educational guides (e.g., ADB Basics, Driver Installation).
7.  **Tutorial Detail Screen**: Comprehensive step-by-step reading view with safety warnings.
8.  **Reports Screen**: List of saved local preparation reports and export options.
9.  **Settings Screen**: Theme toggles, unit preferences, and app information.

### Visual Identity & Theme
- **Aesthetic**: Utilitarian Dark (Terminal style).
- **Color Palette**:
    - `Background`: Deep Charcoal (`#0B0E11`)
    - `Primary`: Neon Cyan (`#00F3FF`)
    - `Status Success`: Acid Green (`#39FF14`)
    - `Status Warning/Unknown`: Amber / Off-white.
- **Typography**:
    - **JetBrains Mono**: For logs, device properties, and code-like data.
    - **Inter/Roboto**: For instructions and tutorial body text.

## 3. Key Product Decisions & Trade-Offs

- **Decision 1: No Automated Scripts**
    - *Chosen Approach*: Purely educational. No `adb shell` execution for modifications, no bootloader unlocking scripts.
    - *Why*: Maintains high trust and prevents accidental device damage.
- **Decision 2: Contextual Security Info**
    - *Chosen Approach*: Display security patch date for user awareness, but do not use it as a "Rootable/Not Rootable" binary flag.
    - *Why*: Rooting availability is complex and often depends on specific vulnerabilities or bootloader policies not captured by patch date alone.
- **Decision 3: Developer Environment Checker**
    - *Chosen Approach*: Use `Settings.Global` and `Settings.Secure` observers to monitor developer settings in real-time.

## 4. Technical Architecture & Data Strategy

### System Diagram
```unicode
┌───────────────────────────────────────────────────────────┐
│                   RootMate App (Compose)                  │
├───────────────────────────────────────────────────────────┤
│  ┌──────────┐      ┌──────────────┐      ┌─────────────┐  │
│  │ Dashboard│ <──> │  ViewModel   │ <──> │ Navigation  │  │
│  └──────────┘      └──────┬───────┘      └─────────────┘  │
└───────────────────────────┼───────────────────────────────┘
                            ▼
           ┌─────────────────────────────────────┐
           │           Repository Layer          │
           └──────┬───────────────────────┬──────┘
                  ▼                       ▼
    ┌──────────────────────────┐    ┌────────────────────┐
    │     Room Database        │    │ System Property    │
    │ (Checklists, Progress)   │    │ Provider (Audit)   │
    └──────────────────────────┘    └────────────────────┘
```

### Data Model
- **DeviceAudit**: Pojo containing model, androidVersion, apiLevel, securityPatch (nullable), and developerSettingsStatus.
- **ChecklistItem**: Entity storing `category` (Backup, DevEnv, General), `label`, and `isChecked`.
- **TutorialContent**: Static local resource (JSON/Strings) containing formatted safety guides.

### Testing & Compatibility
- **Compilation**: The app MUST compile successfully using the provided Gradle toolchain.
- **API Standards**: Avoid deprecated Android APIs (e.g., use modern `ActivityResultLauncher` instead of `startActivityForResult`).
- **Permission Handling**: Gracefully handle missing or denied permissions (especially for storage or fine-grained system info).
- **OS Support**: Minimum SDK is 24 (Android 7.0), but primary optimization and testing target is Android 10+ (API 29+).

### Interactive Component Mapping
- **AuditLogComponent**: Monospace list showing keys and values, with special styling for `[UNKNOWN]`.
- **BackupTracker**: A persistent checklist that survives app restarts via Room.
- **DevStatusBanner**: A dynamic card that updates as the user toggles Developer Options in system settings.
