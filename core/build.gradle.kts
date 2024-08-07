repositories {
    maven("https://repo.codemc.io/repository/nms")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    // -- okaeri-configs --
    compileOnly("eu.okaeri:okaeri-configs-core:5.0.2")

    // -- okaeri-placeholders --
    api("eu.okaeri:okaeri-placeholders-core:5.0.1")

    // -- kyori --
    api("net.kyori:adventure-text-minimessage:4.17.0")
    api("net.kyori:adventure-platform-bukkit:4.3.4")
    api("net.kyori:adventure-text-serializer-legacy:4.17.0")

    // -- dream-utilities --
    api("cc.dreamcode:utilities:1.4.5")
}