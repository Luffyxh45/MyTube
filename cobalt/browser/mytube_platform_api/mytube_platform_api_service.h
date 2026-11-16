// Copyright 2026 MyTube by Luffy.
// Licensed under the Apache License, Version 2.0.
// See starboard/LICENSE for the license text.

#ifndef COBALT_BROWSER_MYTUBE_PLATFORM_API_MYTUBE_PLATFORM_API_SERVICE_H_
#define COBALT_BROWSER_MYTUBE_PLATFORM_API_MYTUBE_PLATFORM_API_SERVICE_H_

#include <string>

namespace mytube {

// Platform operations owned and maintained by MyTube.
// This layer deliberately has no Mojo or TizenTube interface dependency.
class PlatformApiService {
public:
 static bool InstallAppFromUrl(const std::string& url);
 static std::string GetVersion();
 static std::string GetArchitecture();
 static std::string GetBrandAndModel();
 static void SetFrameRate(float frame_rate);
 static void SetUserAgent(const std::string& user_agent);
};

}  // namespace mytube

#endif  // COBALT_BROWSER_MYTUBE_PLATFORM_API_MYTUBE_PLATFORM_API_SERVICE_H_
