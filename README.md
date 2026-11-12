<p align="center"><img src="docs/assets/logo.png" width="120" alt="Tadarab logo"></p>

# Tadarab | تدرّب

An Android graduation project connecting students with training opportunities and supporting communication between students, training advisors, academic advisors, and administrators.

## Interactive demo

[Open the browser demo](https://lujain-alahmari.github.io/Tadarab/)

**بالعربية:** جرّب التقديم على فرصة تدريب، ثم بدّل إلى دور المشرف لقبول الطلب أو رفضه. العرض يستخدم بيانات وهمية ولا يتصل بالخادم الأصلي.

The `docs/` directory contains a new, browser-based demonstration of the project's workflows. It is **not an Android emulator or a live backend**. All organizations, applications and messages are fictional, stored in memory for the current page session, and never submitted to an external service.

Open `docs/index.html` directly in a browser. No installation or account is required.

1. Browse or filter training opportunities.
2. Submit a sample application as a student.
3. Switch to the training-advisor role and accept or reject the request.
4. Switch back to inspect the updated status and try the simulated messaging interface.
5. Reset the demo to start again.

## Original Android application

- Student registration, profiles and training opportunity browsing.
- Training application tracking and advisor review.
- Training program management.
- Form exchange and messaging.
- Academic advisor and administrator screens.

**Stack:** Java, Android SDK, XML layouts, Volley, Gradle.

## Open the Android source

1. Open this repository in Android Studio.
2. Install Android SDK 32; the project targets SDK 32 and supports API 21 or later.
3. Use JDK 17 for Android Gradle Plugin 8.0.2 and Gradle 8.0.2. Java source compatibility remains Java 8.
4. Allow Gradle to synchronize dependencies. Older third-party dependencies may require maintenance.
5. Configure a compatible backend in `app/src/main/java/com/graduation/kas/tadarab/WebSiteData.java` before using network-backed screens.

### Backend and verification limitations

The supplied archive contains the Android client, not its server implementation or database. The historical backend URL has been replaced with `https://example.invalid/` to prevent accidental requests to an unverified service. Login, real applications, uploads and messaging therefore require a separately configured backend. The Android app has not been built or tested against a live backend in this publication pass.

This is an academic prototype, not a production-ready service. Review authentication, authorization, password handling and file-upload validation before connecting it to real users or personal data. The public source excludes build output, local SDK paths, IDE caches, compiled APKs and machine-specific files.

## Repository layout

```text
app/              Original Android client source and resources
gradle/           Gradle wrapper
docs/             Standalone browser demo with fictional data
README.md         Project overview and setup
```

## Project context

Computer Science graduation project, King Khalid University. Published as part of Lujain Mesfer's portfolio. The browser demo is a later portfolio companion and should not be represented as the original Android application.

No open-source license is asserted for the original project or third-party assets. Their respective ownership and terms remain applicable.

## Repository audit

The published client includes all 165 retained source/resource/wrapper files from the supplied archive. Machine-specific files, build products and caches are intentionally excluded. The Gradle wrapper is aligned to Android Gradle Plugin 8.0.2 (Gradle 8.0.2, JDK 17). The browser demo includes student, training-advisor, academic-advisor and administrator dashboards. The backend and database were not present in the supplied archive; a complete live Android service still requires them. An Android build has not been verified in this environment.
