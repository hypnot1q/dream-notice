repositories {
    maven("https://repo.codemc.io/repository/nms")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    api(project(":minecraft"))

    // -- spigot api -- (base)
    compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")

    // -- okaeri-configs --
    compileOnly("eu.okaeri:okaeri-configs-core:5.0.2")

    // -- dream-utilities --
    implementation("cc.dreamcode:utilities-bukkit:1.4.5")

    // -- x-series --
    implementation("com.github.cryptomorin:XSeries:9.10.0")
}