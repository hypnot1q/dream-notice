# Dream-Notice

[![Build Status](https://github.com/DreamPoland/dream-notice/actions/workflows/gradle.yml/badge.svg)](https://github.com/DreamPoland/dream-notice/actions/workflows/gradle.yml)

A simple and efficient notice library with support for placeholders and mini-messages.

## Supported Platforms

- **Bukkit/Spigot/Paper** (requires relocation)
    - [Bukkit](https://github.com/DreamPoland/dream-notice/tree/master/bukkit)
    - [Bukkit-Adventure](https://github.com/DreamPoland/dream-notice/tree/master/bukkit)
- **Paper** (native support for version 1.18.2+)
    - [Paper-Adventure](https://github.com/DreamPoland/dream-notice/tree/master/paper)
- **Bungee/Waterfall** (requires relocation)
    - [Bungee](https://github.com/DreamPoland/dream-notice/tree/master/bungee)
    - [Bungee-Adventure](https://github.com/DreamPoland/dream-notice/tree/master/bungee)

### Important Notice

**Bukkit-Adventure** and **Bungee-Adventure** require these methods to be called during the
on-enable phase:

```java
BukkitNoticeProvider.create(this);
BungeeNoticeProvider.

create(this);
```

## Maven/Gradle Installation

### Maven

Add the following repository to your `pom.xml`:

```xml

<repository>
  <id>dreamcode-repository-releases</id>
  <url>https://repo.dreamcode.cc/releases</url>
</repository>
```

Add the dependency:

```xml

<dependency>
  <groupId>cc.dreamcode.notice</groupId>
  <artifactId>{platform}</artifactId>
  <version>1.5.7</version>
</dependency>
```

### Gradle

Add the following to your `build.gradle`:

```groovy
repositories {
    maven { url "https://repo.dreamcode.cc/releases" }
}

dependencies {
    implementation "cc.dreamcode.notice:{platform}:1.5.7"
}
```

## Usage Examples

### Standard Example

```java
BukkitNotice.chat("&7Simple test {example}.")
    .

with("example","player1")
    .

hoverEvent(HoverEvent.showText(AdventureLegacy.deserialize("Text.")))
    .

clickEvent(ClickEvent.openUrl("https://dreamcode.cc"))
    .

send(player);
```

### i18n Example

The player is used in `PaperNoticeService#getMessage` as a local provider for i18n service. The
`/translations/` directory serves as an example for i18n files. Refer to `PaperNoticeSerializer` for
a detailed format explanation.

```java
PaperNoticeService noticeService = null; // Initialize the service instance appropriately
noticeService.registerResources(getFile(), getDataFolder(), Set.of("translations"));
noticeService.getMessage(player, "exampleNotice").send(player);
```

### Retrieve Jar File on Velocity

```java
public static File getJarFile(Object plugin) {
  try {
    return new File(
        plugin.getClass().getProtectionDomain().getCodeSource().getLocation().toURI()
    );
  } catch (Exception exception) {
    throw new IllegalStateException(
        "Could not retrieve jar file due to an unexpected exception.", exception
    );
  }
}
```