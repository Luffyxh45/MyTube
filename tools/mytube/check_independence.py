#!/usr/bin/env python3
"""Static guardrails for the MyTube independence branch.

These checks validate repository wiring and protect the inherited playback
engine from accidental edits. They are not a substitute for GN/Android builds
or TV-device regression tests.
"""
from pathlib import Path
import subprocess
import sys

ROOT = Path(__file__).resolve().parents[2]


def require(condition: bool, message: str) -> None:
    if not condition:
        print(f"FAIL: {message}", file=sys.stderr)
        raise SystemExit(1)
    print(f"PASS: {message}")


def read(path: str) -> str:
    file_path = ROOT / path
    require(file_path.is_file(), f"{path} exists")
    return file_path.read_text(encoding="utf-8")


browser_gn = read("cobalt/browser/BUILD.gn")
binder = read("cobalt/browser/cobalt_browser_interface_binders.cc")
adapter_h = read("cobalt/browser/mytube_platform_api/mytube_platform_api_impl.h")
java_gn = read("cobalt/android/BUILD.gn")
platform_java = read("cobalt/android/apk/app/src/main/java/dev/cobalt/coat/MyTubePlatformApi.java")
mojom = read("cobalt/browser/h5vcc_tizentube/public/mojom/h5vcc_tizentube.mojom")
manifest = read("cobalt/shell/android/shell_apk/AndroidManifest.xml.jinja2")
shell_gn = read("cobalt/shell/android/BUILD.gn")

require('"//cobalt/browser/mytube_platform_api"' in browser_gn,
        "browser target builds the MyTube platform adapter")
require('"//cobalt/browser/h5vcc_tizentube/public/mojom"' in browser_gn,
        "legacy web compatibility contract remains available")
require('"//cobalt/browser/h5vcc_tizentube",' not in browser_gn,
        "obsolete TizenTube native implementation target is not linked")
require('&mytube::PlatformApiImpl::Create' in binder,
        "browser binder routes the compatibility API to MyTube code")
require('h5vcc_tizentube::mojom::H5vccTizentube' in binder,
        "existing web-facing Mojo interface remains registered")
require('h5vcc_tizentube::mojom::H5vccTizentube' in adapter_h,
        "MyTube adapter implements the retained compatibility interface")
require('Copyright 2026 Reis Can' in mojom and 'Apache License, Version 2.0' in mojom,
        "retained third-party Mojo attribution and licence are preserved")

for obsolete in (
    "cobalt/browser/h5vcc_tizentube/BUILD.gn",
    "cobalt/browser/h5vcc_tizentube/h5vcc_tizentube_impl.h",
    "cobalt/browser/h5vcc_tizentube/h5vcc_tizentube_impl.cc",
):
    require(not (ROOT / obsolete).exists(), f"obsolete implementation removed: {obsolete}")

require('"apk/app/src/main/java/dev/cobalt/coat/MyTubePlatformApi.java"' in java_gn,
        "MyTube Android helper is included in the GN Java source list")
require('MATCH_CONTENT_FRAMERATE_SEAMLESS_ONLY' in platform_java,
        "Android frame-rate preference constant is correctly spelled")
require('MATCH_CONTENT_FRAMERATE_SEAMLESSS_ONLY' not in platform_java,
        "frame-rate preference typo is absent")
require('VideoSurfaceView.getCurrentSurface()' in platform_java,
        "frame-rate helper uses the shared video surface")
require('instanceof CobaltActivity' not in platform_java
        and '((CobaltActivity)' not in platform_java,
        "platform helper does not depend on the non-Starboard activity class")
require('android:icon="@drawable/mytube_icon"' in manifest,
        "Android launcher uses the MyTube icon")
require('android:banner="@drawable/mytube_banner"' in manifest,
        "Android TV launcher uses the MyTube banner")
require('"java/res/drawable/mytube_icon.xml"' in shell_gn
        and '"java/res/drawable/mytube_banner.xml"' in shell_gn,
        "MyTube vector branding resources are packaged")
for density in ("hdpi", "mdpi", "xhdpi", "xxhdpi", "xxxhdpi"):
    require(not (ROOT / f"cobalt/shell/android/java/res/drawable-{density}/app_banner.png").exists(),
            f"obsolete TizenTube banner removed ({density})")

for licence in ("LICENSE", "LICENSE.chromium_os", "starboard/LICENSE", "AUTHORS"):
    require((ROOT / licence).is_file(), f"upstream notice retained: {licence}")

# Protect the media/player implementation from branding/backend refactors.
diff = subprocess.run(
    ["git", "diff", "--quiet", "origin/main...HEAD", "--",
     "media/", "cobalt/media/", "cobalt/android/apk/app/src/main/java/dev/cobalt/media/"],
    cwd=ROOT,
    check=False,
)
require(diff.returncode == 0,
        "no media pipeline or Android media implementation changes versus main")

print("MyTube static guardrails passed. Build and device tests are still required.")
