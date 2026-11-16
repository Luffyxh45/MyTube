# MyTube

**MyTube by Luffy** is an Android TV-focused video streaming app built on the Cobalt runtime, with a familiar TV-style viewing experience.

## Features

- **TV-focused interface:** Designed for navigation and viewing on supported TV devices.
- **Ad blocking:** Watch supported video content with fewer interruptions.
- **SponsorBlock:** Automatically skip supported sponsored segments.
- **Video speed controls:** Adjust playback speed where supported.
- **DeArrow integration:** Help replace clickbait titles and thumbnails where supported.
- **Cobalt runtime:** Uses the Cobalt platform for supported devices.

## Android TV builds

The repository includes a GitHub Actions workflow for building Android TV APKs for:

- **ARM 32-bit** (armeabi-v7a)
- **ARM 64-bit** (arm64-v8a)

Builds are produced by the repository's [Android TV APK workflow](https://github.com/Luffyxh45/MyTube/actions/workflows/mytube-android-apk.yml). When a workflow run succeeds, its APK files are available from that run's Artifacts section. A successful build is required before an APK can be offered as an installable release.

## Installation

1. Open the [GitHub Actions workflow runs](https://github.com/Luffyxh45/MyTube/actions/workflows/mytube-android-apk.yml).
2. Select a successful run.
3. Download the artifact matching your device architecture.
4. Extract the APK and sideload it onto your Android TV device.

## Support

- [Report an issue or request a feature](https://github.com/Luffyxh45/MyTube/issues)

## Project identity and licensing

MyTube is the project name and **MyTube by Luffy** is its maintainer branding. MyTube is based on the Cobalt project and incorporates other open-source components; branding this project does not transfer ownership of those upstream components. Their original copyright notices, licences, and required attributions remain applicable. See the repository's licence and third-party notices for details.
