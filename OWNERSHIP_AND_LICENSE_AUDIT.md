# MyTube Ownership & License Audit

**Repository:** `Luffyxh45/MyTube`  
**Branch audited:** `mytube-branding`  
**Audited revision:** `21cb7394e83f75e2f49613ac511be339d2fbcab7` (2026-11-16)  
**Status:** Initial repository-level audit — **not yet a complete file-by-file legal audit**.

## Important ownership conclusion

MyTube can be our project identity and we can own the original code, artwork, documentation, and modifications that we independently create and have the rights to contribute. A fork does not transfer copyright in inherited code to the fork owner. We must not replace upstream authors' copyright notices with "MyTube by Luffy", remove required licence texts, or claim exclusive ownership of third-party components.

The correct goal is: **MyTube by Luffy as the product/project brand, with transparent attribution and compliance for every inherited component.**

## Repository size and audit limitation

The GitHub recursive tree endpoint reports 57,538 entries / 53,007 file entries for this revision, but the response is marked truncated. This prevents a defensible claim that every file has been individually reviewed. A complete audit needs to enumerate the full tree in smaller directory batches and inspect source headers, per-package metadata, assets, generated files, and build/runtime dependencies.

## Verified top-level findings

| Path / area | What was inspected | Current classification | Required treatment |
|---|---|---|---|
| `LICENSE` | Chromium copyright notice and BSD-style 3-clause terms | Third-party / upstream licence | Preserve the full text and Chromium attribution where applicable. |
| `LICENSE.chromium_os` | ChromiumOS copyright notice and BSD-style 3-clause terms | Third-party / upstream licence | Preserve the full text and ChromiumOS attribution where applicable. |
| `AUTHORS` | Large upstream contributor list | Third-party contributor record | Do not replace with the MyTube author name. |
| `starboard/LICENSE` | Apache License 2.0 text | Third-party / Starboard licence | Preserve it and satisfy Apache notice requirements for redistributed material. |
| `starboard/README.md` | Describes Starboard as Cobalt's platform abstraction layer | Upstream project documentation | Keep technical attribution where relevant; rewrite only if creating genuinely original MyTube documentation. |
| `cobalt/CHANGELOG.md` | Cobalt release history and Chromium/Blink integration notes | Upstream project history | Do not relabel historical upstream work as MyTube-authored. |
| `third_party/README.md` | Explicitly says this directory contains sources from other projects | Third-party dependency area | Audit each package's own metadata and licence; do not claim these sources as ours. |
| `third_party/README.chromium.template` | Requires package name, upstream URL, version/revision, licence, licence file, shipped status and local modifications | Dependency compliance convention | Use each package's `README.chromium` and licence file as the inventory source. |
| `third_party/` | Contains many separately named dependency packages | Third-party dependencies | Each package must be inventoried individually; preserve its licence and notices. |
| `cobalt/` | Cobalt runtime source tree | Inherited upstream code plus any later MyTube changes | Identify file-level authorship from history and headers; retain applicable Cobalt/Chromium notices. |
| `.gitmodules` | Still contains numerous upstream Chromium/Cobalt and platform-specific submodule declarations, including conditional internal/test dependencies | Upstream build/dependency configuration | Review each entry for build necessity and access; do not remove wholesale. |
| `.github/workflows/` | Chromium/Cobalt CI, autoroll and platform build workflows | Inherited project automation | Keep only workflows we actually use; update project-specific branding and secrets safely. |
| `.github/assets/` | Three assets named `TizenTube Cobalt Banner.png`, `TizenTube Download Latest Release Button.png`, and `TizenTube_Cobalt-Official_Banner.png` | TizenTube-branded assets; provenance not yet established | Verify references and rights, then replace with original MyTube artwork or remove if unused. |
| `README.md` | Identifies the project as MyTube, credits "MyTube by Luffy", says it is built on Cobalt, and acknowledges third-party licences | MyTube project documentation | Keep accurate; do not imply the whole codebase is exclusively authored by MyTube. |

## What is and is not ours

### Can be attributed to MyTube / Luffy when independently created
- The MyTube name and original project identity, subject to trademark clearance.
- Original MyTube logos, icons, banners, screenshots and other artwork that we create or have permission to use.
- Original MyTube documentation, scripts and code written by us.
- Our original modifications, to the extent copyrightable and not copied from upstream code.
- Our original integration/configuration work, subject to the licence of the files or projects it modifies.

### Not automatically owned by MyTube
- Chromium and ChromiumOS source code and contributor work.
- Cobalt and Starboard source code and contributor work.
- Packages under `third_party/` and their upstream maintainers' work.
- Submodules, imported code, fonts, media, trademarks, icons or other assets from external projects.
- Contributions made by other people, unless there is a valid assignment or licence granting the required rights.

## Immediate issues to investigate

1. **TizenTube banners:** inspect every reference to the three image assets; replace only after confirming whether they are used by README, release templates, workflows or documentation.
2. **Remaining TizenTube identity:** run case-insensitive searches across all tracked text files and inspect binary/image metadata. A zero-result code search is not proof of absence because GitHub code search did not return matches for this branch.
3. **Per-file notices:** identify source files with copyright/SPDX headers and classify them by upstream author/project. Never bulk-rewrite those headers.
4. **Third-party package inventory:** enumerate every direct child of `third_party/`, then inspect each package's `README.chromium`, licence files, version, upstream URL, shipped status and local modifications. Packages without clear metadata need manual review.
5. **Cobalt and Starboard inventory:** identify upstream source files versus MyTube-authored modifications using Git history and file headers.
6. **Build and release artifacts:** inspect app identifiers, package metadata, artwork, About/credits UI, generated notices and CI release workflows. Verify that all distributed binaries carry the required notices.
7. **Submodules and private dependencies:** distinguish unused platform/test-only entries from dependencies required for supported builds before removing anything.

## Audit rules for every file

Each file must receive one of these classifications:

- **MYTUBE-ORIGINAL** — created by the MyTube team; authorship and rights are evidenced.
- **MYTUBE-MODIFIED-UPSTREAM** — derived from upstream; MyTube changes are documented, but upstream notices and licence remain.
- **UPSTREAM-CHROMIUM** — Chromium/ChromiumOS-originated.
- **UPSTREAM-COBALT-STARBOARD** — Cobalt/Starboard-originated.
- **THIRD-PARTY-DEPENDENCY** — imported dependency with its own licence and attribution.
- **ASSET-REVIEW-REQUIRED** — image, font, media or other asset whose source/permission is not yet verified.
- **GENERATED / BUILD OUTPUT** — generated files; identify generator and source inputs.
- **UNKNOWN / MANUAL REVIEW** — provenance or applicable licence cannot yet be established.

For each file, record: path, classification, detected copyright holder, SPDX/licence identifier or licence file, upstream URL/revision where available, whether shipped in the product, local modifications, required notices, and audit evidence.

## Release gate

Do not call the repository or binary "fully owned by MyTube" and do not publish a release as licence-audited until:
- the full tree has been enumerated without truncation;
- every tracked file has a classification or an explicit manual-review status;
- every shipped dependency and asset has verified rights and notices;
- applicable source-disclosure and redistribution conditions are met;
- the generated credits / third-party notices are included in the release;
- the supported build and packaging workflows pass.

This document is an engineering audit record, not legal advice. Unclear provenance or licence compatibility should be reviewed by a qualified open-source licensing professional before distribution.


## Supplemental verification — 2026-11-16

This update records additional checks against the live `mytube-branding` branch at `21cb7394e83f75e2f49613ac511be339d2fbcab7`.

### Repository inventory counts

| Scope | Verified result | Meaning |
|---|---:|---|
| Full recursive Git tree response | 57,538 entries returned; 53,007 blobs, 4,522 trees and 9 gitlinks | GitHub marks this response **truncated**. It is not a complete file inventory. |
| `third_party/` immediate child directories | 303 | These are dependency/project directories, not 303 files or 303 verified licences. |
| `third_party/` immediate gitlink entries | 28 | These point to separate repositories/revisions and need their own provenance/licence review. |
| Root `.gitmodules` | 1,060 lines; 69 occurrences of `chrome-internal.googlesource.com` | These include conditional internal/test/platform dependencies. They are not all necessarily checked out or shipped. Do not remove them wholesale. |

### Additional files and dependency metadata inspected

| Path | Evidence observed | Classification / action |
|---|---|---|
| `OWNERSHIP_AND_LICENSE_AUDIT.md` | Added by a commit authored by the repository owner, titled `docs: add initial MyTube ownership and license audit` | MYTUBE-ORIGINAL audit documentation; it records findings and limitations, not a legal certification. |
| `third_party/abseil-cpp/README.chromium` | Abseil; Apache-2.0; `Shipped: yes`; upstream URL and revision recorded | THIRD-PARTY-DEPENDENCY; retain Apache-2.0 licence and required notices. |
| `third_party/boringssl/README.chromium` | BoringSSL; MIT, BSD-3-Clause, OpenSSL, ISC and SSLeay; `Shipped: yes` | THIRD-PARTY-DEPENDENCY; multiple licence texts/conditions require package-specific review. |
| `third_party/angle/README.chromium` | ANGLE; BSD-3-Clause; `Shipped: yes` | THIRD-PARTY-DEPENDENCY; preserve BSD notices. |
| `third_party/zlib/README.chromium` | zlib 1.3.1; Zlib licence; `Shipped: yes` | THIRD-PARTY-DEPENDENCY; preserve zlib notice. |
| `third_party/libvpx/README.chromium` | libvpx; BSD-3-Clause and Patent; `Shipped: yes` | THIRD-PARTY-DEPENDENCY; review both licence and patent terms/notices. |
| `.github/assets/TizenTube Cobalt Banner.png` | Asset exists in the branch | ASSET-REVIEW-REQUIRED; source/creator and permission not verified. |
| `.github/assets/TizenTube Download Latest Release Button.png` | Asset exists in the branch | ASSET-REVIEW-REQUIRED; source/creator and permission not verified. |
| `.github/assets/TizenTube_Cobalt-Official_Banner.png` | Asset exists in the branch | ASSET-REVIEW-REQUIRED; source/creator and permission not verified. |

### Important clarification about the existing cleanup

The five root-level gitlinks previously removed (`clank`, `internal`, `ios_internal`, `native_client`, and `signing_keys`) are no longer the only upstream dependency references in this very large source tree. The `.gitmodules` file still contains many conditional upstream submodule declarations, including 69 private Google-source URL occurrences. These are build/dependency metadata, not evidence that MyTube owns the referenced repositories. Their relevance to MyTube's supported build must be established before any further removal.

### File-level audit status

The following is the **sample of individual files actually inspected in this pass**, not a claim that all 53,007 blobs have been read:

- `LICENSE` — Chromium BSD-style licence and Chromium copyright notice.
- `LICENSE.chromium_os` — ChromiumOS BSD-style licence and ChromiumOS copyright notice.
- `AUTHORS` — upstream contributor list.
- `README.md` — current MyTube project description and third-party acknowledgement.
- `OWNERSHIP_AND_LICENSE_AUDIT.md` — owner-authored audit record.
- `cobalt/CHANGELOG.md` — upstream Cobalt release history.
- `starboard/LICENSE` — Apache License 2.0 text.
- `starboard/README.md` — Starboard/Cobalt technical documentation.
- `third_party/README.md` and `third_party/README.chromium.template` — upstream dependency inventory rules.
- The five `README.chromium` files listed in the dependency table above.
- `.gitmodules` — upstream submodule declarations and conditions.

No full source-header scan, image metadata/provenance scan, binary asset review, full dependency licence scan, Android package/runtime identity audit, or supported-platform build was completed in this pass. A genuine file-by-file audit still requires a complete non-truncated file list and automated scanning (for example ScanCode/REUSE or an equivalent SCA tool), followed by manual review of ambiguous and shipped components.

### Immediate next work

1. Generate a complete file manifest by recursively splitting the tree into directory-level API requests until every directory is enumerated; retain the manifest as an audit artifact.
2. Run copyright/SPDX/licence detection across all source and documentation files; map each finding to the exact path and evidence.
3. Inventory all 303 `third_party/` package directories and 28 gitlinks, including nested dependencies, package metadata, upstream revision, shipped status, and full licence texts.
4. Identify the actual Cobalt/Starboard targets used by MyTube and separate those from unrelated Chromium platform/test code before deciding what can be removed.
5. Trace every TizenTube-branded asset/reference, verify image provenance, and replace only with original artwork or remove if genuinely unused.
6. Inspect all files modified by MyTube commits against their upstream parent versions to distinguish original work from derivative changes.
7. Verify generated third-party notices in the final Android/TV release and run the supported build/test workflows.

**No inherited copyright or licence notice has been changed as part of this supplemental audit.** This update does not certify the repository or any binary as exclusively owned by MyTube.


## Follow-up pass — 2026-11-16

**Branch revision after this pass:** `020f8e71c430f647dabd3a50830c0ba2026ea4aa`.

This section supersedes the earlier statements that the three TizenTube-named GitHub assets and the old funding file were still present.

### Verified MyTube identity changes committed

| File | Verified change | Rights / caveat |
|---|---|---|
| `cobalt/android/BUILD.gn` | Android manifest package value changed from `io.gh.reisxd.tizentube.cobalt` to `com.luffy.mytube`. | The file's Cobalt copyright and Apache-2.0 header were preserved. Build/package validation is still required. |
| `cobalt/shell/android/shell_apk/AndroidManifest.xml.jinja2` | Default Android application label changed from `TizenTube` to `MyTube`. | The Cobalt copyright and Apache-2.0 header were preserved. The YouTube TV app URL and splash-screen metadata were not changed. |
| `.github/FUNDING.yml` | Removed. It directed GitHub Sponsors and Buy Me a Coffee funding to `reisxd`, not MyTube. | No MyTube funding destination was supplied, so no replacement funding account was invented. |
| `.github/assets/TizenTube Cobalt Banner.png` | Removed. | The asset's provenance/permission was not established. |
| `.github/assets/TizenTube Download Latest Release Button.png` | Removed. | The asset's provenance/permission was not established. |
| `.github/assets/TizenTube_Cobalt-Official_Banner.png` | Removed. | The asset's provenance/permission was not established. |

The three TizenTube-named image assets were not referenced by any of the 66 text/metadata files scanned under `.github/` or by the current root README. This is a bounded reference scan, not proof that no external GitHub metadata or unscanned file ever referenced them.

### TizenTube-derived code that must not be falsely claimed

The following files were inspected directly:

- `cobalt/browser/h5vcc_tizentube/BUILD.gn`
- `cobalt/browser/h5vcc_tizentube/h5vcc_tizentube_impl.cc`
- `cobalt/browser/h5vcc_tizentube/h5vcc_tizentube_impl.h`
- `cobalt/browser/h5vcc_tizentube/public/mojom/BUILD.gn`
- `cobalt/browser/h5vcc_tizentube/public/mojom/h5vcc_tizentube.mojom`
- `cobalt/browser/BUILD.gn`

The H5VCC module files carry **Copyright 2026 Reis Can (reisxd)** and an **Apache License 2.0** header. The browser build explicitly depends on this module. It implements app installation from a URL, version/device information, frame-rate control, and user-agent override functionality. These files are therefore not safe to delete or relabel as MyTube-original without a compatibility and provenance review. Their original copyright headers must remain; the Apache licence must be supplied with distributions, and modifications must be marked as required by that licence.

### Permission / repository administration clarification

- `.github/OWNERS` contains two upstream reviewer email addresses. It is not GitHub's collaborator/admin permission list and does not itself grant those people repository write or admin access.
- The repository connector available for this audit can check a named collaborator's permission, but it does not expose a complete collaborator list. Actual repository access should be reviewed in **GitHub → Settings → Collaborators and teams**. No collaborator permissions were changed in this pass.
- `.github/workflows/autoroll_main.yaml` is inherited Cobalt automation. Its scheduled trigger is commented out; it is manually dispatchable and uses the `CHERRY_PICK_TOKEN` secret to push autoroll branches / create pull requests. It is not the MyTube Android app build. It was left unchanged pending a decision about whether upstream Cobalt autoroll maintenance is still wanted.

### Current verified conclusion

The app-facing Android package value and label now identify as MyTube, and the three stale TizenTube GitHub banner assets plus the unrelated upstream funding file have been removed. The Cobalt/Chromium engine, Starboard, and third-party libraries remain inherited licensed components. The TizenTube-derived H5VCC module remains in the build and must retain its Reis Can attribution and Apache-2.0 terms.

**This is still not a completed file-by-file legal audit.** The recursive Git tree is truncated; the 303 third-party package directories, 28 third-party gitlinks, full source headers, binary assets, shipped dependency set, and generated release notices have not all been individually inspected. No supported Android build or release packaging test has been run after the package-ID change. Do not describe the whole repository as exclusively owned by MyTube or as fully licence-cleared until those checks pass.
