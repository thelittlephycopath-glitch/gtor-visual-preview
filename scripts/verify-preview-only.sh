#!/usr/bin/env bash
set -euo pipefail

manifest="app/src/main/AndroidManifest.xml"
source_dir="app/src/main/java/org/gtor/visualpreview"

if [[ ! -f "$manifest" || ! -d "$source_dir" ]]; then
  echo "Preview-only check failed: expected app files were not found."
  exit 1
fi

if grep -Eq '<uses-permission|<service|android\.permission\.(INTERNET|BIND_VPN_SERVICE)|android\.net\.VpnService' "$manifest"; then
  echo "Preview-only check failed: the manifest includes a permission or service outside the UI-only scope."
  exit 1
fi

if grep -R -nE 'import android\.net\.VpnService|VpnServiceCommand|OkHttpClient|HttpURLConnection|Retrofit|java\.net\.(Socket|URL)' "$source_dir"; then
  echo "Preview-only check failed: network or VPN implementation was detected in the preview UI source."
  exit 1
fi

echo "Preview-only boundary verified: no manifest permissions/services or VPN/network implementation detected."
