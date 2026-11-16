// Copyright 2026 MyTube by Luffy.
// Licensed under the Apache License, Version 2.0.
// See starboard/LICENSE for the license text.

#include "cobalt/browser/mytube_platform_api/mytube_platform_api_impl.h"

#include <utility>

#include "cobalt/browser/mytube_platform_api/mytube_platform_api_service.h"
#include "content/public/browser/render_frame_host.h"

namespace mytube {

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
  std::move(callback).Run(PlatformApiService::InstallAppFromUrl(url));
}

void PlatformApiImpl::GetVersion(GetVersionCallback callback) {
  std::move(callback).Run(PlatformApiService::GetVersion());
}

void PlatformApiImpl::GetArchitecture(GetArchitectureCallback callback) {
  std::move(callback).Run(PlatformApiService::GetArchitecture());
}

void PlatformApiImpl::GetBrandAndModel(GetBrandAndModelCallback callback) {
  std::move(callback).Run(PlatformApiService::GetBrandAndModel());
}

void PlatformApiImpl::SetFrameRate(float frame_rate) {
  PlatformApiService::SetFrameRate(frame_rate);
}

void PlatformApiImpl::SetUserAgent(const std::string& user_agent) {
  PlatformApiService::SetUserAgent(user_agent);
}

}  // namespace mytube
