# GTor Visual Preview

A small, public Android **visual preview** of the GTor connection screen.

## Scope

- UI-only demonstration; the button changes local preview text.
- No VPN tunnel, Tor integration, network requests, backend, analytics, or persistent storage.
- No secrets or credentials are required.
- This is **not a working VPN** and does not provide privacy or anonymity protection.

## Build an APK

The GitHub Actions workflow builds a debug APK on pushes to `main` and can be started manually from **Actions → Build GTor Visual Preview APK → Run workflow**.

After a successful run, download the artifact named `gtor-visual-preview-debug-apk` from the run's **Artifacts** section.

## Local build

Install JDK 17, Android SDK platform 35, and Gradle 8.9, then run:

```sh
gradle --no-daemon :app:assembleDebug
```

The APK should be produced at `app/build/outputs/apk/debug/app-debug.apk`.

## Privacy

The manifest requests no Android permissions. Keep credentials, signing keys, private endpoints, personal data, and production implementation out of this repository. Review commit metadata and workflow changes before contributing.

## License

MIT. See [LICENSE](LICENSE).
