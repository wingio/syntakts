plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("com.vanniktech.maven.publish.base")
}

setup(
    libName = "Syntakts for Compose",
    moduleName = "syntakts-compose",
    moduleDescription = "Support for Syntakts rendering in Compose"
)

kotlin {
    androidTarget()
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