import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    id("com.android.library")
    id("maven-publish")
}

android {
    namespace = "com.lb.auto_fit_textview"
    compileSdk = 36

    defaultConfig {
        minSdk = 23
    }
    lint {
        targetSdk = 36
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    tasks.withType<KotlinJvmCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

afterEvaluate {
    publishing {
        publications {
            register<MavenPublication>("release") {
                from(components["release"])
                // Keep artifactId as Repo name for JitPack to find it easily if requested as com.github.User:Repo:Version
                artifactId = "AutoFitTextView"
            }
        }
    }
}

dependencies {
    implementation("com.google.android.material:material:1.13.0")
    implementation("androidx.core:core-ktx:1.17.0")
}
