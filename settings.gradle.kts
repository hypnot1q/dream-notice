rootProject.name = "dream-notice"

include(":core")
include(":core-adventure-i18n")

// -- minecraft core --
include(":minecraft")
include(":minecraft-adventure")

// -- platforms --
include(":bukkit")
include(":bukkit-adventure")
include(":bungee")
include(":bungee-adventure")
include(":paper-adventure")

// -- serializers (okaeri-configs) --
include(":serializer:bukkit-serializer")
include(":serializer:bukkit-adventure-serializer")
include(":serializer:bungee-serializer")
include(":serializer:bungee-adventure-serializer")
include(":serializer:paper-adventure-serializer")
include("paper-adventure-i18n")
