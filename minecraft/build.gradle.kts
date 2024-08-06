repositories {
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    api(project(":core"))

    // -- okaeri-configs --
    compileOnly("eu.okaeri:okaeri-configs-core:5.0.2")

    // -- bungeecord-chat --
    compileOnly("net.md-5:bungeecord-chat:1.20-R0.1-SNAPSHOT")
}