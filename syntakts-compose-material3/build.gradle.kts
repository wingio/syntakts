plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("com.vanniktech.maven.publish.base")
}

setup(
    libName = "Syntakts for Compose (Material 3)",
    moduleName = "syntakts-compose-material3",
    moduleDescription = "Adds Material 3 theming support to Syntakts components"
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
                api(project(":syntakts-compose"))
                api(compose.material3)
            }
        }
    }
}