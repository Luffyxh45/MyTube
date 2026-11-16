# Building MyTube Android TV APKs

This repository uses Cobalt's Android CoAT build pipeline. The supported native Android ABI targets in this branch are `android-arm` (32-bit ARM / `armeabi-v7a`) and `android-arm64` (64-bit ARM / `arm64-v8a`). The documented Cobalt packaging target is `cobalt_install`, which produces `out/<platform>_gold/cobalt.apk`.

## GitHub Actions builder

Workflow: [MyTube Android TV APK](../.github/workflows/mytube-android-apk.yml)

The workflow currently uses GitHub's standard hosted `ubuntu-latest` runner. No personal Linux computer, server, or self-hosted runner registration is required. It is configured to attempt both Android ABIs sequentially:

- `android-arm` — 32-bit ARM / `armeabi-v7a`.
- `android-arm64` — 64-bit ARM / `arm64-v8a`.

**Resource warning:** Cobalt/Chromium Android builds are unusually large. A standard GitHub-hosted runner has substantially less disk space and memory than the dedicated private runner used by some upstream projects. The workflow now warns when less than 80 GB is available, but deliberately continues so we can measure the actual failure point. Builds may fail from disk exhaustion, memory pressure, missing host dependencies, or the hosted job time limit. A failed attempt does not mean the source code itself is broken.

The workflow checks the available tools, installs Chromium `depot_tools` in the temporary runner directory, configures each ABI with `cobalt/build/gn.py`, builds `cobalt_install`, checks the APK archive and expected ABI directory, calculates SHA-256, and uploads separate 90-day APK artifacts when successful. It also uploads a 14-day diagnostic artifact containing build metadata and captured configure/build logs, including when a build fails.

## Run a build

Open **Actions → MyTube Android TV APK → Run workflow** and select the `mytube-branding` branch. The workflow also runs automatically when relevant source, workflow, or build-documentation files are pushed to that branch.

Expected artifacts if both builds complete:

- `MyTube-android-arm-APK` — 32-bit ARM candidate.
- `MyTube-android-arm64-APK` — 64-bit ARM candidate.

If the hosted runner runs out of resources, the next step is a larger cloud VM or a dedicated self-hosted Linux machine. Merely changing a runner label to an upstream project's private label does not grant access to its machines.

## Release safety

A successful workflow run proves that the configured target compiled and produced an APK archive with the expected ABI directory. It does **not** prove installation, playback, YouTube TV compatibility, SponsorBlock behavior, signing for public distribution, or that the generated CoAT shell is the final production Kimono app. Those checks remain required before publishing a public release. The workflow therefore uploads build and diagnostic artifacts only; it does not create a public GitHub Release or sign with a production key. Git history remains the source-code backup. GitHub Actions artifacts are temporary backups, not permanent archival storage. A separate OneDrive/Google Drive copy can be added only after the account owner authorizes a secure upload integration; no cloud-storage credentials are committed to this repository.

The generated debug/development APK must be treated as a test candidate until its package ID, launcher branding, install/upgrade behavior, and TV playback are verified on supported hardware.
