This is a Kotlin Multiplatform project targeting Android, iOS, Desktop. Coding was done by cursor by giving multiple prompts in order to correct the image rendering since this is webp images.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.



![Screenshot_1](https://github.com/aikrn29feb/AnimeKmp/blob/main/screenshots/anime_desktop.png)
![Screenshot_2](https://github.com/aikrn29feb/AnimeKmp/blob/main/screenshots/anime_desktop_imgs.png)
![Screenshot_3](https://github.com/aikrn29feb/AnimeKmp/blob/main/screenshots/anime_desktop_logs.png)
![Screenshot_4](https://github.com/aikrn29feb/AnimeKmp/blob/main/screenshots/anime_desktop_logs_webp_images.png)
![Screenshot_5](https://github.com/aikrn29feb/AnimeKmp/blob/main/screenshots/anime_android_emulator.png)

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…# AnimeKMP
