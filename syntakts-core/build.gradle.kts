import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
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

    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    applyHierarchyTemplate {
        common {
            group("nonJs") {
                group("jvm") {
                    withAndroidTarget()
                    withJvm()
                }
                group("native") {
                    group("ios") {
                        withIosX64()
                        withIosArm64()
                        withIosSimulatorArm64()
                    }

                    group("mac") {
                        withMacosX64()
                        withMacosArm64()
                    }
                }
            }
        }
    }

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