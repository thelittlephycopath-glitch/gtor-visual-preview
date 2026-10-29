# GTor Visual Preview

An Android-only UI/UX sandbox for experimenting with the future GTor experience.

## Scope

This repository is deliberately a visual prototype. It does **not** implement or connect a VPN, create a tunnel, access the network, query installed applications, authenticate users, call a backend, or collect analytics. Region, bridge, appearance, and app-routing choices are local preview state intended to demonstrate screen flows.

## Screens

- **Home:** connection-status concept, illustrative overview cards, quick settings.
- **Regions:** locally selectable sample regions; these are not live Tor exit nodes.
- **Settings:** region selection, bridges, app-routing mock, appearance, privacy notes, and About.
- **Detail flows:** sample bridge choices, an app-routing toggle, appearance preference, and product-safety explanations.
- **Branding:** simplified white-mask GTor logo, app-header mark, in-app brand mark, and adaptive launcher icon.

## Build

The GitHub Actions workflow **Build GTor Visual Preview APK** builds a debug APK and uploads the artifact as `gtor-visual-preview-debug-apk`. Open the repository's Actions tab, choose the latest successful run, and download the artifact ZIP.

## Important

This is a design prototype—not a production VPN client. UI states and example labels must not be interpreted as real connection, privacy, or protection status. The original GTor/Tor VPN repository is not changed by this preview project.
