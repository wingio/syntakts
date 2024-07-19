plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("com.vanniktech.maven.publish.base")
    alias(libs.plugins.binary.compatibility)
    alias(libs.plugins.compose.compiler)
}

setup(
    libName = "Syntakts for Compose",
    moduleName = "syntakts-compose",
    moduleDescription = "Support for Syntakts rendering in Compose"
)

kotlin {
    androidTarget() {
        publishLibraryVariants("release")
    }
    jvm()

    jvmToolchain(17)
    explicitApi()

    sourceSets {
        commonMain {
            dependencies {
                api(project(":syntakts-core"))
                api(compose.runtime)
                api(compose.ui)
                api(compose.foundation)
                implementation(libs.uuid)
            }
        }
    }
}