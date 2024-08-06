import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1" // or your desired Shadow version
}

repositories {
    maven("https://repo.codemc.io/repository/nms")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":core-adventure-i18n"))
    implementation(project(":minecraft"))
    implementation(project(":minecraft-adventure"))
    implementation(project(":paper-adventure"))
    implementation(project(":serializer:paper-adventure-serializer"))

    // -- paper api -- (core)
    compileOnly("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")

    // -- okaeri-configs --
    compileOnly("eu.okaeri:okaeri-configs-core:5.0.2")

    // -- kyori-adventure --
    compileOnly("net.kyori:adventure-text-minimessage:4.17.0")

    // -- dream-utilities --
    implementation("cc.dreamcode:utilities:1.4.5")
}

tasks.withType<ShadowJar> {
    manifest {
        attributes["Main-Class"] = "cc.dreamcode.notice.i18n.locale.Main"
    }
}