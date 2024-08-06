plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "dream-notice"

// -- core --
include(":core")

// -- minecraft core --
include(":minecraft")

// -- platforms --
include(":paper")
include(":velocity")
