// Copyright 2026 MyTube by Luffy.
// Licensed under the Apache License, Version 2.0.
// See starboard/LICENSE for the license text.

#include "cobalt/browser/mytube_platform_api/mytube_platform_api_service.h"

#include "build/build_config.h"
#include "cobalt/browser/global_features.h"

#if BUILDFLAG(IS_ANDROID)
#include "base/android/jni_android.h"
#include "starboard/android/shared/starboard_bridge.h"
#endif  // BUILDFLAG(IS_ANDROID)

namespace mytube {

#if BUILDFLAG(IS_ANDROID)
using base::android::AttachCurrentThread;
using starboard::StarboardBridge;
#endif

bool PlatformApiService::InstallAppFromUrl(const std::string& url) {
#if BUILDFLAG(IS_ANDROID)
  JNIEnv* env = AttachCurrentThread();
  return StarboardBridge::GetInstance()->InstallAppFromURL(env, url.c_str());
#else
  return false;
#endif
}

std::string PlatformApiService::GetVersion() {
#if BUILDFLAG(IS_ANDROID)
  JNIEnv* env = AttachCurrentThread();
  return StarboardBridge::GetInstance()->GetVersion(env);
#else
  return "unknown";
#endif
}

std::string PlatformApiService::GetArchitecture() {
#if BUILDFLAG(IS_ANDROID)
  JNIEnv* env = AttachCurrentThread();
  return StarboardBridge::GetInstance()->GetArchitecture(env);
#else
  return "unknown";
#endif
}

std::string PlatformApiService::GetBrandAndModel() {
#if BUILDFLAG(IS_ANDROID)
  JNIEnv* env = AttachCurrentThread();
  return StarboardBridge::GetInstance()->GetBrandAndModel(env);
#else
  return "unknown";
#endif
}

void PlatformApiService::SetFrameRate(float frame_rate) {
#if BUILDFLAG(IS_ANDROID)
  JNIEnv* env = AttachCurrentThread();
  StarboardBridge::GetInstance()->SetFrameRate(env, frame_rate);
#endif
}

void PlatformApiService::SetUserAgent(const std::string& user_agent) {
  cobalt::GlobalFeatures::GetInstance()->SetSettings(
      "user_agent_override", user_agent);
}

}  // namespace mytube
