repositories {
    maven("https://repo.codemc.io/repository/nms")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://storehouse.okaeri.eu/repository/maven-public/")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":minecraft"))
    implementation(project(":minecraft-adventure"))

    // -- okaeri-configs --
    compileOnly("eu.okaeri:okaeri-configs-core:5.0.2")

    // -- kyori-adventure --
    compileOnly("net.kyori:adventure-text-minimessage:4.17.0")

    // -- dream-utilities --
    implementation("cc.dreamcode:utilities:1.4.5")
}