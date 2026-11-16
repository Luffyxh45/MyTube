// Copyright 2026 MyTube by Luffy.
// Licensed under the Apache License, Version 2.0.
// See starboard/LICENSE for the license text.

#ifndef COBALT_BROWSER_MYTUBE_PLATFORM_API_MYTUBE_PLATFORM_API_IMPL_H_
#define COBALT_BROWSER_MYTUBE_PLATFORM_API_MYTUBE_PLATFORM_API_IMPL_H_

#include <string>

#include "cobalt/browser/h5vcc_tizentube/public/mojom/h5vcc_tizentube.mojom.h"
#include "content/public/browser/document_service.h"
#include "mojo/public/cpp/bindings/pending_receiver.h"

namespace content {
class RenderFrameHost;
}  // namespace content

namespace mytube {

// MyTube-owned implementation behind the legacy H5VCC compatibility contract.
class PlatformApiImpl
    : public content::DocumentService<
          h5vcc_tizentube::mojom::H5vccTizentube> {
 public:
  static void Create(
      content::RenderFrameHost* render_frame_host,
      mojo::PendingReceiver<h5vcc_tizentube::mojom::H5vccTizentube> receiver);

  PlatformApiImpl(const PlatformApiImpl&) = delete;
  PlatformApiImpl& operator=(const PlatformApiImpl&) = delete;

  void InstallAppFromURL(const std::string& url,
                         InstallAppFromURLCallback callback) override;
  void GetVersion(GetVersionCallback callback) override;
  void GetArchitecture(GetArchitectureCallback callback) override;
  void GetBrandAndModel(GetBrandAndModelCallback callback) override;
  void SetFrameRate(float frame_rate) override;
  void SetUserAgent(const std::string& user_agent) override;

 private:
  PlatformApiImpl(
      content::RenderFrameHost& render_frame_host,
      mojo::PendingReceiver<h5vcc_tizentube::mojom::H5vccTizentube> receiver);
};

}  // namespace mytube

#endif  // COBALT_BROWSER_MYTUBE_PLATFORM_API_MYTUBE_PLATFORM_API_IMPL_H_
