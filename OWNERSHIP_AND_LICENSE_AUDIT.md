# MyTube Ownership & License Audit

**Repository:** `Luffyxh45/MyTube`  
**Branch audited:** `mytube-branding`  
**Audited revision:** `b4f1afe9c25a1276aa6842939b488a1003fb921f`  
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
