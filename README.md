# Dream-Notice

[![Build](https://github.com/DreamPoland/dream-notice/actions/workflows/gradle.yml/badge.svg)](https://github.com/DreamPoland/dream-notice/actions/workflows/gradle.yml)

Simple notice library with placeholders and mini-messages support.

## Platforms

- Bukkit/Spigot/Paper (need
  relocation) - [bukkit](https://github.com/DreamPoland/dream-notice/tree/master/bukkit)
  or [bukkit-adventure](https://github.com/DreamPoland/dream-notice/tree/master/bukkit)
- Paper (native-support
  1.18.2+) - [paper-adventure](https://github.com/DreamPoland/dream-notice/tree/master/paper-adventure)
- Bungee/Waterfall (need
  relocation) - [bungee](https://github.com/DreamPoland/dream-notice/tree/master/bukkit)
  or [bungee-adventure](https://github.com/DreamPoland/dream-notice/tree/master/bukkit)

### Warning

Bukkit-Adventure and Bungee-Adventure require these methods: (on-enable)

`BukkitNoticeProvider.create(this)` or `BungeeNoticeProvider.create(this)`

## Maven/Gradle

### Maven

```xml
<repository>
  <id>dreamcode-repository-releases</id>
  <url>https://repo.dreamcode.cc/releases</url>
</repository>
```

```xml
<dependency>
  <groupId>cc.dreamcode.notice</groupId>
  <artifactId>{platform}</artifactId>
  <version>1.5.7</version>
</dependency>
```

### Gradle

```groovy
maven { url "https://repo.dreamcode.cc/releases" }
```

```groovy
implementation "cc.dreamcode.notice:{platform}:1.5.7"
```

## Example standard

```java
BukkitNotice.chat("&7Simple test {example}.")
    .with("example","player1")
    .hoverEvent(HoverEvent.showText(AdventureLegacy.deserialize("Text.")))
    .clickEvent(ClickEvent.openUrl("https://dreamcode.cc"))
    .send(player);
```

## Example i18n

Player is used in PaperNoticeService#getMessage as a local provider for i18n service.

```java
  PaperNoticeService noticeService = null;
  noticeService.registerResources(getFile(), getDataFolder(), Set.of("translations"));
  noticeService.getMessage(player, "exampleNotice").send(player);
```

## Get JarFile on Velocity

```java
  public static File getJarFile(Object plugin) {
  try {
    return new File(
            plugin.getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
  } catch (Exception exception) {
    throw new IllegalStateException(
            "Could not retrieve jar file, because of unexpected exception.", exception);
  }
}
```