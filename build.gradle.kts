buildscript {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
        maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev" )
    }

    dependencies {
        classpath(libs.plugin.maven)
        classpath(libs.plugin.multiplatform.compose)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
    }
}

allprojects {
    group = "xyz.wingio.syntakts"
    version = "1.0.0-SNAPSHOT"
}