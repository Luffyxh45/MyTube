# MyTube Independence Plan

**Project:** MyTube by Luffy  
**Repository:** `Luffyxh45/MyTube`  
**Working branch:** `mytube-branding`  
**Status:** Independence work in progress — not yet a standalone engine or a complete licence audit.

## Goal

Maintain and develop MyTube without requiring TizenTube's owner, repository, servers, release pipeline, credentials, or approval. Over time, replace TizenTube-specific implementation code with MyTube-maintained components while preserving the YouTube TV compatibility behavior that the application actually needs.

This plan does not claim that inherited Chromium, Cobalt, Starboard, or third-party code is owned by MyTube. Existing copyright notices, licences, and third-party attributions remain in force unless a component is replaced with independently authored code and its provenance is verified.

## Independence has three separate meanings

1. **Operational independence:** MyTube can be built, tested, signed, released, and maintained using MyTube-controlled infrastructure and credentials.
2. **Code-provenance independence:** TizenTube-specific code is replaced by independently authored MyTube implementations. Retained upstream code remains attributed and licensed correctly.
3. **Engine independence:** A longer-term option to replace or substantially reduce reliance on the inherited Cobalt/Chromium engine. This is a major separate engineering programme; it is not achieved by renaming the fork.

## Product compatibility contract — preserve the existing TizenTube experience

The approved product direction is **MyTube = the existing YouTube TV experience, with MyTube branding and an ad-free experience**. Independence work must not turn into a player rewrite or a UI redesign.

The following are preservation requirements:

- Keep the existing YouTube TV interface, navigation, layout, and interaction model unchanged unless the owner explicitly requests a UI change.
- Preserve the existing TizenTube playback behaviour and capabilities: the current video pipeline, stream/format handling, buffering behaviour, playback controls, seek behaviour, playback speed, captions/subtitles, and every resolution/quality option that currently works on the supported device.
- Preserve current automatic/manual quality selection and adaptive quality behaviour wherever already supported. Do not claim support for a resolution, codec, HDR mode, or feature that has not been verified on the target device.
- Keep the existing SponsorBlock integration and sponsor-segment skipping, along with the existing ad-skipping behaviour, subject to applicable platform terms and law.
- Treat the Cobalt/Chromium media stack and the existing YouTube TV web application as compatibility-critical. Do not replace, simplify, disable, or bulk-edit their media/player code as part of branding or backend independence work.
- Make backend and platform changes behind the existing compatibility boundary. A change is acceptable only when it preserves the observable playback/UI behaviour or has an explicitly approved reason to change it.

### Required regression gate before playback-related changes

1. Record the current supported device/build and a working playback baseline before touching media code.
2. Test playback start, pause/resume, seek, playback speed, captions, available quality levels, manual quality changes, adaptive quality, long playback/buffering, and audio/video stability.
3. Verify SponsorBlock detection/skipping and existing ad-skipping behaviour separately from ordinary playback.
4. Compare the same tests after each relevant change. If the environment cannot run the target build, mark the behaviour **not verified**; do not infer success from source inspection.
5. Keep playback and UI code out of scope for a backend-only refactor unless a concrete dependency requires a change.

## Verified starting point

The current branch has a MyTube-owned native platform implementation registered in the Cobalt browser. The obsolete TizenTube-specific C++ implementation target and source files have been removed from the active tree. The public Mojo interface remains under `cobalt/browser/h5vcc_tizentube/public/mojom/` as a compatibility contract for the existing YouTube TV client.

- `cobalt/browser/mytube_platform_api/` contains the MyTube C++ adapter and internal service.
- `cobalt/browser/cobalt_browser_interface_binders.cc` binds the existing web-facing Mojo contract to `mytube::PlatformApiImpl`.
- `cobalt/browser/BUILD.gn` depends on the MyTube implementation plus the legacy public Mojom target; it no longer depends on the deleted legacy implementation target.
- `starboard/android/shared/starboard_bridge.h` and `base_starboard_bridge.cc` provide the existing Android JNI bridge.
- `cobalt/android/apk/app/src/main/java/dev/cobalt/coat/BaseStarboardBridge.java` delegates platform operations to the new MyTube Java helper, `MyTubePlatformApi.java`.

The retained Mojom definition carries Reis Can's original Apache-2.0 copyright notice and must remain attributed unless a proven compatible migration replaces it. This local compatibility dependency does not establish that MyTube contacts TizenTube-owned servers or requires the former owner's approval.

The existing `OWNERSHIP_AND_LICENSE_AUDIT.md` is the evidence log. Its scope limitations remain important: the recursive Git tree was truncated, the full file inventory is incomplete, and no production build/runtime validation has yet been completed.

## Work phases and safety gates

### Phase 1 — Establish a reliable baseline

- Keep all work on `mytube-branding`; do not make unreviewed changes to `main`.
- Record the exact branch revision and compare it with the base branch before each change.
- Identify the actual supported production target, package identity, build arguments, signing flow, and release workflow.
- Trace the web-facing H5VCC API contract and every native/Java call site before changing the interface.
- Capture a reproducible build/test baseline where the required build environment is available.

**Gate:** No native integration is removed until its consumers and production target are known.

### Phase 2 — Make the TizenTube integration replaceable

- Define a MyTube-owned internal interface and adapter boundary.
- Preserve the existing web-facing compatibility contract initially if the YouTube TV application requires it.
- Implement equivalent behavior in MyTube-maintained code without copying implementation text unnecessarily.
- Keep upstream notices on any retained or modified derivative files.
- Add tests for each behavior: app install/deep link, version, architecture, device identity, frame-rate control, picture-in-picture, feature checks, and dialogs, as applicable to the supported target.

**Gate:** The replacement must compile and pass behavior tests before build references to the old module are changed.

### Phase 3 — Remove the TizenTube-specific dependency

- Switch browser registration and GN dependencies to the MyTube implementation.
- Remove obsolete TizenTube-specific implementation files only after all call sites are migrated.
- Remove TizenTube-specific bridge methods only after proving they have no remaining callers.
- Search tracked text, build metadata, assets, workflows, release templates, and generated credits for remaining TizenTube references.
- Keep historical attribution where legally required; do not erase provenance from retained code.

**Gate:** A clean supported build and runtime regression test must pass.

### Phase 4 — Own the product pipeline

- Use MyTube-controlled repository workflows, release tags, signing keys, update endpoints, and documentation.
- Remove unused upstream autoroll/release automation only after confirming it is not needed for supported builds.
- Ensure no required build step depends on a private TizenTube owner's token, server, or manual approval.
- Publish generated third-party notices with every distributable build.

**Gate:** Verify a release build from a clean checkout using only documented MyTube-controlled steps.

### Phase 5 — Longer-term engine independence

- Evaluate which Cobalt/Chromium components are essential and which can be replaced.
- Keep a separately tracked architecture plan and compatibility test suite.
- Replace components incrementally; do not attempt a wholesale engine rewrite as a branding task.
- Preserve all upstream licence obligations for code that remains in the product.

## Licensing and ownership rules

- Do not replace upstream copyright headers with "MyTube by Luffy".
- Do not delete or overwrite root, directory, package, or per-file licence notices as a branding cleanup.
- Do not apply one new licence to the entire mixed-provenance repository.
- MyTube may license original code and assets only where the authorship and rights are established.
- Every third-party dependency and shipped asset must retain its applicable licence and required notices.
- Treat unknown provenance as **MANUAL REVIEW REQUIRED**, not as MyTube-owned.
- This engineering document is not legal advice.

## Current completion status

- [x] Project direction approved: full independence from TizenTube and its owner.
- [x] Initial native integration and build wiring identified.
- [x] Existing ownership/licence audit limitations documented.
- [ ] Full tracked-file inventory completed without truncation.
- [ ] Complete source, asset, and dependency provenance review completed.
- [ ] Supported production build target and baseline verified.
- [x] MyTube-owned Android platform backend implemented and wired behind the legacy H5VCC interface.
- [ ] Supported Cobalt/Android build and runtime tests passed.
- [x] Obsolete legacy TizenTube C++ implementation target and source files removed safely.
- [ ] Legacy TizenTube-named Mojo compatibility contract migrated after web-client consumer contract is verified.
- [ ] MyTube-only build and release pipeline verified.
- [ ] Third-party notices generated and checked in release artifacts.

## Immediate next engineering task

Trace the complete H5VCC contract from the YouTube TV web application through Mojo, C++, Starboard JNI, and Java. Identify which methods are actually called by the supported application and which are unused. Use that call graph to design the smallest compatible MyTube replacement.


## Implementation log — MyTube backend extraction

**Branch revision after this step:** `2d400d6c16e64de042363f0c8a81469eb33f1769`

A first MyTube-owned replacement layer has now been added without deleting the legacy compatibility contract:

- `cobalt/android/apk/app/src/main/java/dev/cobalt/coat/MyTubePlatformApi.java` contains newly authored Android platform operations for package version, device identity, CPU ABI, frame-rate requests, picture-in-picture, feature checks, dialogs, and APK installation.
- `BaseStarboardBridge.java` keeps its JNI entry-point names but delegates those operations to `MyTubePlatformApi`.
- `cobalt/browser/mytube_platform_api/` contains a MyTube-owned C++ `DocumentService` implementation.
- `cobalt/browser/BUILD.gn` and `cobalt_browser_interface_binders.cc` now build and register the MyTube implementation behind the existing H5VCC Mojo interface.
- The legacy interface remains at `cobalt/browser/h5vcc_tizentube/public/mojom/` as a compatibility contract. The old TizenTube implementation source files are still present but are no longer the implementation registered by the browser target.
- The native Starboard bridge comments now describe these as MyTube platform operations. Existing Chromium/Cobalt copyright and licence notices were not replaced.

The APK installer implementation now accepts HTTPS URLs only, validates HTTPS redirects, applies connection/read timeouts and a 100 MiB size limit, checks that the downloaded ZIP contains `AndroidManifest.xml`, and routes installation through Android's user-confirmed package installer. It also reports failures to the Android log instead of silently swallowing them.

**Not yet verified:** no GN generation, C++/Java build, APK packaging, device runtime test, or automated regression test has been run. The old Mojo interface and old source files must not be removed until the supported target builds and the YouTube TV consumer contract is verified.


The follow-up Java cleanup removed imports made unused by the extraction. Static source checks confirm the main browser target and binder reference the MyTube implementation, while the legacy Mojo type remains intentionally in place. No compile or runtime test has yet been run.


## Implementation log — native cleanup and Android build wiring review — 2026-11-16

The unused legacy C++ implementation was removed from the active branch:

- Deleted `cobalt/browser/h5vcc_tizentube/BUILD.gn`.
- Deleted `cobalt/browser/h5vcc_tizentube/h5vcc_tizentube_impl.h`.
- Deleted `cobalt/browser/h5vcc_tizentube/h5vcc_tizentube_impl.cc`.
- Kept the public Mojom contract and its original attribution, because the current binder still exposes that contract to the existing web client.
- Added comments to the browser binder and GN dependency to distinguish the retained compatibility contract from the removed implementation.

A further static build-file review found that `MyTubePlatformApi.java` was not listed in the `cobalt_main_java` GN source list. It has now been added. The same review found a misspelled Android frame-rate preference constant and an unnecessary dependency on `CobaltActivity`, which is not part of every Starboard Android source configuration. The helper now uses the shared active `VideoSurfaceView` surface directly and uses the correctly named Android preference constant.

These are source-level corrections only. The repository has not been compiled in this session; GN generation, C++/Java compilation, APK packaging and target-device tests remain unverified. The complete licence inventory and production release pipeline also remain outstanding.
