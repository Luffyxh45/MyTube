# MyTube

**MyTube by Luffy** is an Android TV-focused streaming app built on the Cobalt runtime. The project aims to retain the familiar TV viewing experience while presenting it under MyTube branding.

## Features

- **TV-friendly interface:** A viewing experience designed for television devices and remote navigation.
- **Ad blocking:** Reduce supported advertising interruptions.
- **SponsorBlock:** Skip supported sponsored segments.
- **Playback controls:** Retain supported playback and video quality controls.
- **Video speed control:** Adjust playback speed where supported.
- **DeArrow:** Replace misleading titles and thumbnails where supported.

## Android TV builds

MyTube's build workflow targets both Android ARM architectures:

- **32-bit ARM:** `armeabi-v7a`
- **64-bit ARM:** `arm64-v8a`

Open the [MyTube Android APK workflow](https://github.com/Luffyxh45/MyTube/actions/workflows/mytube-android-apk.yml) to view build runs. After a run succeeds, download the matching APK from its **Artifacts** section. Builds are not considered available until the workflow has completed successfully.

## Installation

1. Open the [MyTube Android APK workflow](https://github.com/Luffyxh45/MyTube/actions/workflows/mytube-android-apk.yml).
2. Select a successful run.
3. Download the APK artifact for your device architecture.
4. Extract the APK and sideload it onto your Android TV device.

## Support

- [Report issues or request features](https://github.com/Luffyxh45/MyTube/issues)
- [View source code](https://github.com/Luffyxh45/MyTube)

## Project identity and licensing

MyTube is the project name; **MyTube by Luffy** is the project branding. This repository is based on the Cobalt project and may include other third-party open-source components. MyTube branding does not change the ownership or licensing of upstream code. Original copyright notices, licence terms, and required attributions must remain intact. Refer to the repository's licence files and third-party notices for details.
