# GTor Visual Preview

An Android-only UI/UX sandbox for experimenting with the future GTor experience.

## Scope

This repository is deliberately a visual prototype. It does **not** implement or connect a VPN, create a tunnel, access the network, query installed applications, authenticate users, call a backend, or collect analytics. Region, bridge, appearance, and app-routing choices are local preview state intended to demonstrate screen flows.

## Screens

- **Home:** connection-status concept, illustrative overview cards, quick settings.
- **Regions:** locally selectable sample regions with search; these are not live Tor exit nodes.
- **Settings:** region selection, bridges, app-routing mock, appearance, privacy notes, and About.
- **Detail flows:** sample bridge choices, an app-routing toggle, appearance preference, and product-safety explanations.
- **Branding:** simplified white-mask GTor logo, app-header mark, in-app brand mark, and adaptive launcher icon.

## APK Downloads

**Latest build page:** [Open GTor Preview APK builds](https://github.com/thelittlephycopath-glitch/gtor-visual-preview/actions/workflows/build-preview-apk.yml)

1. Open the build page above and select the newest successful run (green check).
2. Scroll to **Artifacts** and download **`gtor-visual-preview-debug-apk`**.
3. Extract the ZIP on your phone. It contains the debug APK and `SHA256SUMS.txt`.
4. Open the APK on your Android device to install it. Android may ask you to allow installation from the app you used to open the file.

### What the build system checks

- Runs `scripts/verify-preview-only.sh` before compiling, to guard the UI-only scope.
- Builds the Android debug APK with GitHub Actions.
- Fails if the build does not produce exactly one APK.
- Generates a SHA-256 checksum so the downloaded APK can be checked for integrity.
- Keeps each uploaded artifact for **14 days**.

This is a **debug preview APK**, not a signed production release. A successful build means the APK compiled; it does not by itself prove every screen has been tested on a physical device.

## Important

This is a design prototype—not a production VPN client. UI states and example labels must not be interpreted as real connection, privacy, or protection status. The original GTor/Tor VPN repository is not changed by this preview project.
