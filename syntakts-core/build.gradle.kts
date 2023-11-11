plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.vanniktech.maven.publish.base")
}

setup(
    libName = "Syntakts Core",
    moduleName = "syntakts-core",
    moduleDescription = "Easy to use text parsing and syntax highlighting library"
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
                compileOnly(libs.compose.stable.marker)
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