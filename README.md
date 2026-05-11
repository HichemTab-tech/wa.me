# Tap to wa.me

Tap to wa.me is a small Android utility that helps you open a WhatsApp chat from a phone number without saving that number to your contacts first.

It reads a phone number from the clipboard on launch, cleans it up, lets you review or edit it, and then opens the matching `https://wa.me/<number>` link.

## What it does

- Prefills the phone number field from the clipboard.
- Sanitizes pasted or typed input before opening the chat link.
- Supports quick redirection to `wa.me` from a simple Compose UI.

## Privacy

The app reads clipboard content locally on your device to help prefill the number field.

- Clipboard data is not stored.
- Clipboard data is not sent to any server.
- Clipboard data is not shared with third parties.
- The app only opens the generated `wa.me` link in the appropriate app or browser.

Privacy page: `https://hichemtab-tech.github.io/wa.me/privacy.html`

## Disclaimer

This project is an independent open source utility.

It is not related to, endorsed by, sponsored by, or affiliated in any way with Meta or WhatsApp.

## Tech

- Kotlin
- Jetpack Compose
- Android Studio / Gradle

## Build

```bash
./gradlew assembleDebug
```

On Windows:

```powershell
.\gradlew.bat assembleDebug
```

## Built By

@HichemTab-tech