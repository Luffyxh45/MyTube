# MyTube Independence Roadmap

**Repository:** `Luffyxh45/MyTube`
**Working branch:** `mytube-branding`
**Status checked:** 2026-11-16
**Purpose:** Make MyTube independently maintainable and releasable without relying on TizenTube or its owner, while preserving all applicable upstream licences.

## Independence goal

MyTube will own and control its project identity, application-layer code that we independently create, build/release automation, signing and distribution process, documentation, and maintenance decisions.

There are two different goals:

- **Operational independence from TizenTube:** MyTube can build, maintain, package and release without TizenTube's owner, repository, CI, private services, credentials or approval.
- **Runtime independence:** MyTube eventually replaces the inherited Cobalt/Chromium engine with a separately implemented engine. This is a much larger project and is not achieved by renaming or relicensing Cobalt.

A fork does not transfer copyright. Existing Chromium, Cobalt, Starboard, TizenTube-derived and third-party code must keep the notices and licence terms that apply to it. MyTube may license its own original code separately, once authorship and rights are clear.

## Verified repository facts

- The repository is a very large Chromium/Cobalt-derived source tree, not a small standalone application repository.
- The recursive Git tree response was marked **truncated** (more than 57,000 entries were returned). A complete file-by-file audit is not yet possible from that response.
- Root `LICENSE` contains Chromium BSD-style terms; `LICENSE.chromium_os` contains ChromiumOS BSD-style terms.
- `starboard/LICENSE` contains Apache-2.0 terms. Cobalt and many other files also carry their own copyright/licence headers.
- `AUTHORS` is an inherited contributor record and must not be replaced with a MyTube-only list.
- `third_party/` contains hundreds of dependency directories; individual packages and nested dependencies require their own licence review.
- `.gitmodules` still contains many upstream submodule declarations, including conditional platform, test and internal Google-hosted dependencies. Their presence does not prove they are required by MyTube's supported build. Do not remove them wholesale.
- Five root-level gitlinks (`clank`, `internal`, `ios_internal`, `native_client`, `signing_keys`) were removed on this branch in earlier cleanup. This did not remove all upstream dependencies or all similarly named conditional submodule declarations.
- The three TizenTube-named banner assets under `.github/assets/` and the inherited `.github/FUNDING.yml` were removed on this branch. The old assets should no longer be described as present.
- `README.md` identifies MyTube as a Cobalt-based streaming app and credits the project maintainer as MyTube by Luffy. It also acknowledges third-party licences.
- `cobalt/android/BUILD.gn` sets `com.luffy.mytube` for the CoAT development-shell manifest generation. This is **not proof** that the production Kimono APK uses that package ID.
- `cobalt/shell/android/shell_apk/AndroidManifest.xml.jinja2` labels the CoAT development shell as MyTube. Its own comments explicitly say this manifest does not affect the production Kimono app. The YouTube TV URL and splash-screen metadata remain unchanged.
- The unused TizenTube-derived native implementation target and C++ implementation files have now been removed from `mytube-branding`. The public Mojo contract remains under `cobalt/browser/h5vcc_tizentube/public/mojom/` because the current binder still exposes that interface to the existing web client. Its original **Copyright 2026 Reis Can (reisxd)** and Apache-2.0 header is retained. The binder now connects that compatibility interface to `mytube::PlatformApiImpl`; the browser GN target no longer depends on the old implementation target.
- The inspected `.github/OWNERS` file lists Chromium reviewers. It is a code-review ownership file, not GitHub collaborator permissions and does not itself grant repository write/admin access.
- The latest branch comparison at this audit reports `mytube-branding` is 63 commits ahead of `main` and 0 behind. This branch has not been merged into `main`.

## Safe rules

1. Never replace upstream copyright notices with a MyTube notice.
2. Never remove licence texts or third-party notices merely to make the repository look independently owned.
3. Do not delete a dependency, module, submodule, workflow or source directory until its use in the supported production build is understood.
4. Keep MyTube-authored work in clearly scoped files and commits; document source and provenance.
5. Preserve third-party attribution in releases and About/credits material where required.
6. Use original MyTube artwork and branding. Do not reuse third-party artwork without verified rights.
7. Do not claim that a fork or renamed Cobalt tree is a new engine.
8. Do not merge changes into `main` or publish a release until the relevant build and licence checks pass.

## Work stages

### Stage 1 — Establish the production baseline

- Locate the actual production app entry point (Kimono or other target), package ID, signing config, app icon/banner, splash screen, and release workflow.
- Identify the exact supported device/platform target and reproduce its current build before changing core dependencies.
- Generate a complete Git manifest by recursively enumerating directory trees in smaller requests; the recursive root response is truncated.
- Inventory tracked files, gitlinks, submodules, DEPS entries, generated files, assets and third-party package metadata.
- Run automated copyright/SPDX/licence scanning, then manually review ambiguous files and all shipped components.
- Record a known-good build and test baseline.

**Exit condition:** We know exactly which target is shipped, how it is built, what it contains and which dependencies it requires.

### Stage 2 — Remove operational dependence on TizenTube

- Search all tracked files and project metadata for TizenTube names, URLs, package identifiers, owner-controlled services and release references.
- Trace the `h5vcc_tizentube` API from the YouTube TV page contract through its Mojo registration and Android Starboard bridge.
- Design a MyTube-owned replacement implementation for the needed API behaviour. Keep a compatibility adapter if the YouTube TV client still expects the old API name.
- Do not delete or rename the existing module until the replacement compiles and compatibility tests demonstrate that install, device info, frame-rate and user-agent features still work.
- Preserve Reis Can's existing copyright and Apache-2.0 notice in any retained or modified derivative files; independently authored replacement files must have accurate authorship and licensing.
- Verify issue tracking, CI, signing, versioning, releases, documentation and credentials are controlled by MyTube.

**Exit condition:** MyTube's build, app-layer runtime services, CI, signing and releases no longer require TizenTube's owner or infrastructure.

### Stage 3 — Own the MyTube application layer

- Isolate MyTube-specific app code, UI, configuration, integrations and packaging from inherited Cobalt runtime code.
- Implement MyTube modules independently and keep them in clearly scoped directories.
- Replace TizenTube-derived app modules one at a time, preserving behaviour with tests.
- Keep a compatibility layer where required by the YouTube TV client.

**Exit condition:** The MyTube application layer is maintained by MyTube and no longer depends on TizenTube-authored application modules.

### Stage 4 — Control and reduce runtime dependence

- Maintain a MyTube-controlled Cobalt fork with pinned upstream revisions, reproducible build instructions and an explicit update policy.
- Reduce unused Chromium platform/test code only after supported target analysis.
- Replace engine components incrementally only when a viable alternative exists and passes compatibility tests.
- Treat a fully independent browser/TV engine as a separate long-term engineering project with its own architecture, implementation, tests and dependency inventory.

**Exit condition:** Any claim of runtime independence is supported by a separate working implementation, not by branding changes.

## Definition of done

MyTube is **operationally independent of TizenTube** when its application code, build, CI, signing, release process, documentation and maintenance no longer require TizenTube or its owner.

MyTube is **runtime-independent** only when it no longer relies on the inherited Cobalt/Chromium runtime and a replacement engine is implemented and verified.

A complete file-by-file licence audit, verified production package identity, generated third-party notices and a successful supported-platform build are release gates. This roadmap is an engineering plan, not legal advice or a licence certification.


## Implementation update — 2026-11-16

The legacy native implementation cleanup and follow-up Android source corrections have been committed to `mytube-branding`:

- Removed the unused legacy `h5vcc_tizentube` C++ implementation target and its `.cc/.h` files. The public Mojom contract remains as a compatibility boundary.
- Added the missing `MyTubePlatformApi.java` entry to the `cobalt_main_java` GN source list. This was a real build-wiring omission found during static review.
- Corrected the misspelled `DisplayManager.MATCH_CONTENT_FRAMERATE_SEAMLESS_ONLY` constant.
- Removed the helper's compile-time dependency on `CobaltActivity` and apply frame-rate changes to the shared active `VideoSurfaceView` surface instead, so the helper does not assume the non-Starboard activity source set is compiled.
- Verified that the shared shell Android resources provide the FileProvider XML path used by the installer and that the AndroidX Core dependency is present in the Java target.

**Still unverified:** the full Android/Cobalt target has not been generated or compiled, and no APK or TV-device test has been run. These source checks are not a substitute for CI/build/runtime evidence. The Mojo interface cannot be renamed safely until the external YouTube TV web client contract is traced. The complete third-party inventory and release signing pipeline remain open.

## Android branding update — 2026-11-16

The shell manifest now points to MyTube-owned vector launcher resources instead of the inherited Cobalt icon and TizenTube-branded Android TV banner. The five old density-specific TizenTube banner PNGs were removed and the new XML resources were added to the Cobalt shell Android resource target. The YouTube TV start URL and splash screen were intentionally left unchanged. APK rendering and production-target packaging still require build/device verification.
