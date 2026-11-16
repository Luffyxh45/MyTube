// Copyright 2026 MyTube by Luffy.
// Licensed under the Apache License, Version 2.0.
// See starboard/LICENSE for the license text.

#include "cobalt/browser/mytube_platform_api/mytube_platform_api_impl.h"

#include <utility>

#include "build/build_config.h"
#include "cobalt/browser/global_features.h"
#include "content/public/browser/render_frame_host.h"

#if BUILDFLAG(IS_ANDROID)
#include "base/android/jni_android.h"
#include "starboard/android/shared/starboard_bridge.h"
#endif  // BUILDFLAG(IS_ANDROID)

namespace mytube {

#if BUILDFLAG(IS_ANDROID)
using base::android::AttachCurrentThread;
using starboard::StarboardBridge;
#endif

void PlatformApiImpl::Create(
    content::RenderFrameHost* render_frame_host,
    mojo::PendingReceiver<h5vcc_tizentube::mojom::H5vccTizentube> receiver) {
  new PlatformApiImpl(*render_frame_host, std::move(receiver));
}

PlatformApiImpl::PlatformApiImpl(
    content::RenderFrameHost& render_frame_host,
    mojo::PendingReceiver<h5vcc_tizentube::mojom::H5vccTizentube> receiver)
    : content::DocumentService<
          h5vcc_tizentube::mojom::H5vccTizentube>(render_frame_host,
                                                 std::move(receiver)) {}

void PlatformApiImpl::InstallAppFromURL(
    const std::string& url,
    InstallAppFromURLCallback callback) {
#if BUILDFLAG(IS_ANDROID)
  JNIEnv* env = AttachCurrentThread();
  const bool accepted =
      StarboardBridge::GetInstance()->InstallAppFromURL(env, url.c_str());
  std::move(callback).Run(accepted);
#else
  std::move(callback).Run(false);
#endif  // BUILDFLAG(IS_ANDROID)
}

void PlatformApiImpl::GetVersion(GetVersionCallback callback) {
#if BUILDFLAG(IS_ANDROID)
  JNIEnv* env = AttachCurrentThread();
  std::string version = StarboardBridge::GetInstance()->GetVersion(env);
#else
  std::string version = "unknown";
#endif  // BUILDFLAG(IS_ANDROID)
  std::move(callback).Run(std::move(version));
}

void PlatformApiImpl::GetArchitecture(GetArchitectureCallback callback) {
#if BUILDFLAG(IS_ANDROID)
  JNIEnv* env = AttachCurrentThread();
  std::string architecture =
      StarboardBridge::GetInstance()->GetArchitecture(env);
#else
  std::string architecture = "unknown";
#endif  // BUILDFLAG(IS_ANDROID)
  std::move(callback).Run(std::move(architecture));
}

void PlatformApiImpl::GetBrandAndModel(GetBrandAndModelCallback callback) {
#if BUILDFLAG(IS_ANDROID)
  JNIEnv* env = AttachCurrentThread();
  std::string brand_and_model =
      StarboardBridge::GetInstance()->GetBrandAndModel(env);
#else
  std::string brand_and_model = "unknown";
#endif  // BUILDFLAG(IS_ANDROID)
  std::move(callback).Run(std::move(brand_and_model));
}

void PlatformApiImpl::SetFrameRate(float frame_rate) {
#if BUILDFLAG(IS_ANDROID)
  JNIEnv* env = AttachCurrentThread();
  StarboardBridge::GetInstance()->SetFrameRate(env, frame_rate);
#endif  // BUILDFLAG(IS_ANDROID)
}

void PlatformApiImpl::SetUserAgent(const std::string& user_agent) {
  cobalt::GlobalFeatures::GetInstance()->SetSettings(
      "user_agent_override", user_agent);
}

}  // namespace mytube
