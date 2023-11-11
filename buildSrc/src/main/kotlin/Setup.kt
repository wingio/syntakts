import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.findByType
import org.gradle.api.provider.Property
import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.SonatypeHost

// Lets us use `=` for assignments
private fun <T> Property<T>.assign(value: T) = set(value)

fun Project.setupAndroid(name: String) {
    val androidExtension: BaseExtension = extensions.findByType<LibraryExtension>()
        ?: extensions.findByType<com.android.build.gradle.AppExtension>()
        ?: error("Could not found Android application or library plugin applied on module $name")

    androidExtension.apply {
        namespace = "xyz.wingio.${name.replace("-", ".")}"
        compileSdkVersion(34)
        defaultConfig {
            minSdk = 21
            targetSdk = 34
        }
    }
}

@Suppress("UnstableApiUsage")
fun Project.setup(
    libName: String,
    moduleName: String,
    moduleDescription: String
) {
    setupAndroid(moduleName)

    val mavenPublishing = extensions.findByType<MavenPublishBaseExtension>() ?: error("Couldn't find maven publish plugin")

    mavenPublishing.apply {
        publishToMavenCentral(SonatypeHost.S01, automaticRelease = true)
        signAllPublications()

        coordinates("xyz.wingio.syntakts", moduleName, System.getenv("LIBRARY_VERSION") ?: version.toString())

        pom {
            name = libName
            description = moduleDescription
            inceptionYear = "2023"
            url = "https://github.com/wingio/syntakts"

            licenses {
                license {
                    name = "MIT License"
                    url = "https://opensource.org/license/mit/"
                }
            }
            developers {
                developer {
                    id = "wingio"
                    name = "Wing"
                    url = "https://wingio.xyz"
                }
            }
            scm {
                url = "https://github.com/wingio/syntakts"
                connection = "scm:git:github.com/wingio/syntakts.git"
                developerConnection = "scm:git:ssh://github.com/wingio/syntakts.git"
            }
        }
    }
}