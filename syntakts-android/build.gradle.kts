plugins {
    kotlin("android")
    id("com.android.library")
    id("com.vanniktech.maven.publish.base")
    alias(libs.plugins.binary.compatibility)
}

setup(
    libName = "Syntakts for Android",
    moduleName = "syntakts-android",
    moduleDescription = "Support for Syntakts rendering on Android",
    androidOnly = true
)

kotlin {
    jvmToolchain(17)
    explicitApi()
}

dependencies {
    api(project(":syntakts-core"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlin.coroutines.core)

    testImplementation(kotlin("test"))
    testImplementation(libs.junit)
}