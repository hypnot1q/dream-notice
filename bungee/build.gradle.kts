repositories {
    maven("https://repo.codemc.io/repository/nms")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    api(project(":minecraft"))

    // -- bunege api -- (base)
    compileOnly("net.md-5:bungeecord-api:1.20-R0.2")

    // -- okaeri-configs --
    compileOnly("eu.okaeri:okaeri-configs-core:5.0.2")

    // -- kyori --
    implementation("net.kyori:adventure-platform-bungeecord:4.3.3")

    // -- dream-utilities --
    implementation("cc.dreamcode:utilities-bungee:1.4.5")
}