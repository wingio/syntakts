plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.vanniktech.maven.publish.base")
    alias(libs.plugins.binary.compatibility)
}

setup(
    libName = "Syntakts for Android",
    moduleName = "syntakts-android",
    moduleDescription = "Support for Syntakts rendering on Android"
)

kotlin {
    androidTarget {
        publishLibraryVariants("release")
    }

    jvmToolchain(17)
    explicitApi()

    sourceSets {
        val androidMain by named("androidMain") {
            dependencies {
                api(project(":syntakts-core"))
                implementation(libs.androidx.core.ktx)
                implementation(libs.kotlin.coroutines.core)
            }
        }

        val androidTest by named("androidUnitTest") {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.junit)
            }
        }
    }
}