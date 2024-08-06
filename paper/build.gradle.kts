repositories {
    maven("https://repo.codemc.io/repository/nms")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    api(project(":minecraft"))

    // -- paper api -- (core)
    compileOnly("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")

    // -- okaeri-configs --
    compileOnly("eu.okaeri:okaeri-configs-core:5.0.2")
}