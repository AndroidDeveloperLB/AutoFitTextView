[![Release](https://img.shields.io/github/release/AndroidDeveloperLB/AutoFitTextView.svg?style=flat)](https://jitpack.io/#AndroidDeveloperLB/AutoFitTextView)

AutoFitTextView
===============

A TextView that automatically fits its font and line count based on its available size and content.

This code is heavily based on [**this StackOverflow thread**][1].

The sample shows how the library handles various parameters being changed on the TextView: width, height, number of lines allowed, and the content (text) itself. You can play with the various properties to see how the library handles them.

Note: The library is updated to use Kotlin DSL, modern AndroidX libraries, and has a minimum SDK requirement of 21.

Preview
--
![preview animation][2]

Import using Gradle (Kotlin DSL)
--

Add JitPack to your `settings.gradle.kts`:

```kotlin
dependencyResolutionManagement {
    repositories {
        ...
        maven { url = uri("https://jitpack.io") }
    }
}
```

Add the dependency to your module `build.gradle.kts`:

```kotlin
dependencies {
    implementation("com.github.AndroidDeveloperLB:AutoFitTextView:XXX")
}
```

You can find the recent version here (replace XXX):

https://jitpack.io/#AndroidDeveloperLB/AutoFitTextView

[1]: http://stackoverflow.com/questions/16017165/auto-fit-textview-for-android/21851239
[2]: https://raw.githubusercontent.com/AndroidDeveloperLB/AutoFitTextView/master/animationPreview.gif
[4]: https://viksaaskool.wordpress.com/2014/11/16/using-auto-resize-to-fit-edittext-in-android/
