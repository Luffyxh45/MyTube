# MyTube Independence Roadmap

**Repository:** `Luffyxh45/MyTube`  
**Working branch:** `mytube-branding`  
**Purpose:** Move MyTube toward independent ownership and maintenance without breaking supported builds or violating upstream licences.

## Independence goal

MyTube will be maintained and released by its own project owner. It must not require TizenTube's owner, repository, release pipeline, branding, private infrastructure, or permission to continue development.

This does **not** mean inherited code becomes MyTube-owned. Copyright and licence obligations for Chromium, Cobalt, Starboard, and third-party components remain in force unless code is independently replaced or the relevant rights are obtained.

## Current repository facts observed

- The repository is a very large Chromium/Cobalt-derived source tree, not a small standalone application repository.
- The root `LICENSE` contains a Chromium BSD-style licence notice.
- `starboard/LICENSE` contains Apache License 2.0.
- `AUTHORS` is an upstream contributor record and must not be replaced with a MyTube-only author list.
- `starboard/README.md` describes Starboard as Cobalt's platform/OS abstraction layer.
- `cobalt/CHANGELOG.md` contains upstream Cobalt release history.
- `.gitmodules` still declares numerous external repositories, including conditional internal Google-hosted dependencies. These must be assessed against actual build targets before removal.
- The existing ownership audit records a truncated recursive Git tree response and explicitly does not certify every file.
- The existing audit identifies TizenTube-named banner assets under `.github/assets/`; their origin, rights, and usage need verification before replacement or deletion.

## Rules that protect the project

1. Never replace upstream copyright notices with a MyTube notice.
2. Never remove a licence or third-party notice just to make the repository look independently owned.
3. Do not remove a submodule, dependency, workflow, or source directory until its use in the supported MyTube build is established.
4. Keep MyTube-authored changes clearly separated in commits and document their provenance.
5. Do not claim the whole repository or binary is exclusively owned by MyTube while upstream code remains.
6. Use original MyTube branding and assets; do not reuse TizenTube artwork unless permission and provenance are verified.
7. Every release must include the notices required by the licences of the code and assets it distributes.

## Work stages

### Stage 1 — Establish a reliable baseline

- Identify the exact MyTube application entry point, supported target(s), package metadata, and release artifacts.
- Record the current build instructions and workflows.
- Produce a complete repository manifest in directory-sized batches; the recursive tree endpoint is truncated.
- Inventory submodules, DEPS entries, generated files, assets, and third-party packages.
- Run source-header and SPDX/licence scans, then manually review ambiguous and shipped components.
- Save a known-good build configuration and test evidence before changing dependencies.

**Exit condition:** We can identify exactly what is built, what is shipped, and which dependencies are required.

### Stage 2 — Remove operational dependence on TizenTube

- Replace TizenTube-specific project links, banners, release references, templates, and About/credits wording with original MyTube material where legally appropriate.
- Remove TizenTube-specific automation only after confirming it is not needed for builds or releases.
- Ensure issues, documentation, CI, versioning, signing, and release publishing are controlled by the MyTube repository.
- Verify there are no required secrets, private repositories, owner-controlled services, or external release jobs belonging to TizenTube.

**Exit condition:** MyTube can be built, tested, documented, and released solely through infrastructure controlled by the MyTube maintainer.

### Stage 3 — Own the application layer

- Isolate MyTube application code, UI, configuration, integrations, and platform packaging from inherited runtime code.
- Maintain original MyTube modules in clearly named locations with appropriate copyright and licence headers.
- Replace inherited TizenTube-specific application modules with independently implemented equivalents, preserving behaviour through tests.
- Keep a compatibility layer where required so the app continues to work while components are replaced.

**Exit condition:** The MyTube application layer is independently maintained and no longer imports or requires TizenTube-owned application modules.

### Stage 4 — Reduce and eventually replace runtime dependence

- First maintain a MyTube-controlled Cobalt fork with pinned, documented upstream revisions and a reproducible build.
- Replace runtime components incrementally only where a realistic alternative exists and can pass compatibility tests.
- If the long-term target is a fully independent engine, design and implement that engine as a separate project. Do not pretend a renamed Cobalt fork is a new engine.
- Preserve all upstream licences for code that remains in the fork.

**Exit condition:** Any claimed runtime independence is backed by a separate implementation, build, tests, and dependency inventory—not just branding or a renamed directory.

## Definition of done

MyTube may be called operationally independent of TizenTube when its build, source maintenance, CI, signing, releases, documentation, and app-layer code no longer require TizenTube or its owner.

MyTube may be called runtime-independent only after it no longer depends on the inherited Cobalt runtime and its replacement is implemented and verified.

A file-by-file ownership/licence audit and a successful supported-platform build are mandatory release gates. This roadmap is an engineering plan, not legal advice or a licence certification.
