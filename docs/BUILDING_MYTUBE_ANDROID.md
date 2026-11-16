# Building MyTube Android TV APKs

This repository uses Cobalt's Android CoAT build pipeline. The supported native Android ABI targets in this branch are `android-arm` (32-bit ARM / `armeabi-v7a`) and `android-arm64` (64-bit ARM / `arm64-v8a`). The documented Cobalt packaging target is `cobalt_install`, which produces `out/<platform>_gold/cobalt.apk`.

## GitHub Actions builder

Workflow: [MyTube Android TV APK](../.github/workflows/mytube-android-apk.yml)

The workflow is deliberately assigned to a **MyTube-controlled self-hosted Linux x64 runner**. A normal GitHub-hosted runner is not a safe assumption for this Chromium/Cobalt build: the source tree and Android toolchains require substantial disk space and memory. Provision a dedicated machine with:

- Linux x86_64 (Ubuntu is the expected environment).
- At least 16 GB RAM; 32 GB is recommended.
- At least 100 GB free disk space for checkout, toolchains, intermediates, and output.
- Java, Python 3, Git, Make, unzip, zip, and the standard Cobalt/Chromium Android build dependencies.
- Android build prerequisites installed using the repository's `build/install-build-deps.sh --android` where supported.
- A persistent, trusted GitHub Actions self-hosted runner registered to this repository with labels: `self-hosted`, `linux`, `x64`, and `mytube-android-builder`.

Register the runner from **Repository Settings → Actions → Runners → New self-hosted runner**. Follow GitHub's generated commands on the builder machine. Do not commit runner tokens, signing keys, or credentials. Run the runner under a dedicated unprivileged account; do not expose it to untrusted pull-request workflows.

The workflow installs Chromium `depot_tools` into the runner's temporary directory when `DEPOT_TOOLS_DIR` is not supplied. It configures each ABI with the repository's `cobalt/build/gn.py`, builds `cobalt_install`, verifies the APK ZIP and expected native ABI directory, calculates SHA-256, and uploads separate 30-day GitHub Actions artifacts.

## Run a build

After the runner is online, open **Actions → MyTube Android TV APK → Run workflow**. The workflow builds the 32-bit and 64-bit APKs sequentially on the registered builder.

Expected artifacts:

- `MyTube-android-arm-APK` — 32-bit ARM candidate.
- `MyTube-android-arm64-APK` — 64-bit ARM candidate.

## Release safety

A successful workflow run proves that the configured target compiled and produced an APK archive with the expected ABI directory. It does **not** prove installation, playback, YouTube TV compatibility, SponsorBlock behavior, signing for public distribution, or that the generated CoAT shell is the final production Kimono app. Those checks remain required before publishing a public release. The workflow therefore uploads build artifacts only; it does not create a public GitHub Release or sign with a production key.

The generated debug/development APK must be treated as a test candidate until its package ID, launcher branding, install/upgrade behavior, and TV playback are verified on supported hardware.
