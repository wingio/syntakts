import org.jetbrains.kotlin.gradle.targets.js.dsl.ExperimentalWasmDsl

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.vanniktech.maven.publish.base")
    alias(libs.plugins.binary.compatibility)
}

setup(
    libName = "Syntakts Core",
    moduleName = "syntakts-core",
    moduleDescription = "Easy to use text parsing and syntax highlighting library"
)

kotlin {
    androidTarget {
        publishLibraryVariants("release")
    }
    jvm()

    iosX64()
    iosArm64()
    iosSimulatorArm64()
    macosX64()
    macosArm64()

    jvmToolchain(17)
    explicitApi()

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlin.coroutines.core)
            }
        }

        commonTest {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.junit)
            }
        }
    }
}