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

## Verified starting point

The current branch contains a TizenTube-derived native integration in the Cobalt browser:

- `cobalt/browser/h5vcc_tizentube/` defines the native H5VCC implementation and Mojo interface.
- `cobalt/browser/BUILD.gn` includes that implementation in the browser dependency graph.
- `cobalt/browser/cobalt_browser_interface_binders.cc` registers the interface binder.
- `starboard/android/shared/starboard_bridge.h` and `base_starboard_bridge.cc` contain bridge support for the integration.
- `cobalt/android/apk/app/src/main/java/dev/cobalt/coat/BaseStarboardBridge.java` contains Android-side bridge methods.

The inspected TizenTube-specific C++ and Mojo files carry Reis Can copyright notices and Apache-2.0 headers. Those notices must not be replaced with MyTube authorship claims. The integration is present in source/build wiring; this does not prove that a running app contacts TizenTube-owned servers or requires owner approval.

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
- [ ] MyTube-owned replacement implemented and tested.
- [ ] TizenTube-specific integration removed safely.
- [ ] MyTube-only build and release pipeline verified.
- [ ] Third-party notices generated and checked in release artifacts.

## Immediate next engineering task

Trace the complete H5VCC contract from the YouTube TV web application through Mojo, C++, Starboard JNI, and Java. Identify which methods are actually called by the supported application and which are unused. Use that call graph to design the smallest compatible MyTube replacement.
