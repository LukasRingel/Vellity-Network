rootProject.name = "network"

// clients
include("clients:access-client")
findProject(":clients:access-client")?.name = "access-client"

include("clients:babbel-client")
findProject(":clients:babbel-client")?.name = "babbel-client"

include("clients:explorer-client")
findProject(":clients:explorer-client")?.name = "explorer-client"

include("clients:economy-client")
findProject(":clients:economy-client")?.name = "economy-client"

include("clients:config-client")
findProject(":clients:config-client")?.name = "config-client"

include("clients:friends-client")
findProject(":clients:friends-client")?.name = "friends-client"

include("clients:usercache-client")
findProject(":clients:usercache-client")?.name = "usercache-client"

include("clients:guardian-client")
findProject(":clients:guardian-client")?.name = "guardian-client"

include("clients:punish-client")
findProject(":clients:punish-client")?.name = "punish-client"

// utilities
include("utilities:utils-configuration")
findProject(":utilities:utils-configuration")?.name = "utils-configuration"

include("utilities:utils-context")
findProject(":utilities:utils-context")?.name = "utils-context"

include("utilities:utils-httpclient")
findProject(":utilities:utils-httpclient")?.name = "utils-httpclient"

include("utilities:utils-httpserver")
findProject(":utilities:utils-httpserver")?.name = "utils-httpserver"

include("utilities:utils-mysql")
findProject(":utilities:utils-mysql")?.name = "utils-mysql"

include("utilities:utils-redis")
findProject(":utilities:utils-redis")?.name = "utils-redis"

include("utilities:utils-httpserver-spring")
findProject(":utilities:utils-httpserver-spring")?.name = "utils-httpserver-spring"

include("utilities:utils-httpserver-sso")
findProject(":utilities:utils-httpserver-sso")?.name = "utils-httpserver-sso"

include("utilities:utils-alert-discord")
findProject(":utilities:utils-alert-discord")?.name = "utils-alert-discord"

include("utilities:utils-audit")
findProject(":utilities:utils-audit")?.name = "utils-audit"

include("utilities:utils-mojang")
findProject(":utilities:utils-mojang")?.name = "utils-mojang"

// components
include("components:access-service")
findProject(":components:access-service")?.name = "access-service"

include("components:babbel-service")
findProject(":components:babbel-service")?.name = "babbel-service"

include("components:economy-service")
findProject(":components:economy-service")?.name = "economy-service"

include("components:explorer-service")
findProject(":components:explorer-service")?.name = "explorer-service"

include("components:friends-service")
findProject(":components:friends-service")?.name = "friends-service"

include("components:config-service")
findProject(":components:config-service")?.name = "config-service"

include("components:usercache-service")
findProject(":components:usercache-service")?.name = "usercache-service"

include("components:guardian-service")
findProject(":components:guardian-service")?.name = "guardian-service"

include("components:worker-service")
findProject(":components:worker-service")?.name = "worker-service"

include("components:punish-service")
findProject(":components:punish-service")?.name = "punish-service"

// common
include("common:access-common")
findProject(":common:access-common")?.name = "access-common"

include("common:babbel-common")
findProject(":common:babbel-common")?.name = "babbel-common"

include("common:economy-common")
findProject(":common:economy-common")?.name = "economy-common"

include("common:explorer-common")
findProject(":common:explorer-common")?.name = "explorer-common"

include("common:friends-common")
findProject(":common:friends-common")?.name = "friends-common"

include("common:usercache-common")
findProject(":common:usercache-common")?.name = "usercache-common"

include("common:config-common")
findProject(":common:config-common")?.name = "config-common"

include("common:guardian-common")
findProject(":common:guardian-common")?.name = "guardian-common"

include("common:worker-common")
findProject(":common:worker-common")?.name = "worker-common"

include("common:punish-common")
findProject(":common:punish-common")?.name = "punish-common"

// minecraft
include("minecraft:minecraft-common")
findProject(":minecraft:minecraft-common")?.name = "minecraft-common"

include("minecraft:minecraft-velocity")
findProject(":minecraft:minecraft-velocity")?.name = "minecraft-velocity"

include("minecraft:minecraft-spigot-common")
findProject(":minecraft:minecraft-spigot-common")?.name = "minecraft-spigot-common"

include("minecraft:minecraft-spigot-gui")
findProject(":minecraft:minecraft-spigot-gui")?.name = "minecraft-spigot-gui"

include("minecraft:minecraft-spigot-item")
findProject(":minecraft:minecraft-spigot-item")?.name = "minecraft-spigot-item"

 // components-web
include("components-web:chatlog-viewer-web")
findProject(":components-web:chatlog-viewer-web")?.name = "chatlog-viewer-web"