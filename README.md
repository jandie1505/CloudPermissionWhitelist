# CloudPermissionWhitelist
A plugin for the CloudNet v3 cloud system, which ensures that you can only join certain tasks with a certain permission.

## This plugin is not developed further. Here is why:
CloudNet has also implemented a feature which allows to set join permissions on tasks. So there is no need for this plugin anymore.  
Here is a list how to replace all features of this plugin without installing another plugin (except a permissions system, which you should already have):
| Feature | Alternative |
|--|--|
| Join permission `cloudpermissionwhitelist.join.<taskName>` | Add the following to the `properties` section of the task configuration: `"requiredPermission": "<your permission>",` (see CloudNet join permission configuration example) |
| Local kick command `/localkick` | Use `/minecraft:kick <Player> [Reason]` |
| Temorary join permissions with `/allowtempjoin` | Use temorary permissions like `/lp user <Player> permission settemp <configured join permission> true <Time>` |
| Disable join protection for all players with `/joinprotection` | Use temporary permissions like `/lp group default permission settemp <configured join permission> true <Time>` |

### CloudNet join permission configuration example
```json
{
  "name": "SilentLobby",
  "runtime": "jvm",
  "hostAddress": "45.81.232.205",
  "javaCommand": "java",
  "nameSplitter": "-",
  "disableIpRewrite": false,
  "maintenance": false,
  "autoDeleteOnStop": true,
  "staticServices": false,
  "groups": [
    "SilentLobby"
  ],
  "associatedNodes": [],
  "deletedFilesAfterStop": [],
  "processConfiguration": {
    "environment": "MINECRAFT_SERVER",
    "maxHeapMemorySize": 512,
    "jvmOptions": [],
    "processParameters": [],
    "environmentVariables": {}
  },
  "startPort": 44955,
  "minServiceCount": 1,
  "templates": [
    {
      "prefix": "SilentLobby",
      "name": "default",
      "storage": "local",
      "priority": 0,
      "alwaysCopyToStaticServices": false
    },
    {
      "prefix": "Global",
      "name": "Lobby",
      "storage": "local",
      "priority": 0,
      "alwaysCopyToStaticServices": false
    }
  ],
  "deployments": [],
  "includes": [],
  "properties": {
    "requiredPermission": "cloudnet.join.silentlobby", # <--- Here is the join permission
    "smartConfig": {
      "enabled": false,
      "priority": 10,
      "maxServices": -1,
      "preparedServices": 0,
      "smartMinServiceCount": 0,
      "splitLogicallyOverNodes": true,
      "directTemplatesAndInclusionsSetup": true,
      "templateInstaller": "INSTALL_ALL",
      "autoStopTimeByUnusedServiceInSeconds": 180,
      "percentOfPlayersToCheckShouldStopTheService": 0,
      "forAnewInstanceDelayTimeInSeconds": 300,
      "percentOfPlayersForANewServiceByInstance": 100
    }
  }
}
```

## Overview
This plugin is a permission whitelist for CloudNet. When installed, it restricts access to servers (services). Only players with the task-specific permission or wildcard permission can join.
  
If you install the plugin, you don't need to configure it. It works out of the box. But there is a configuration if you need it (you should also copy the config into your template, else the update notifications won't work).
  
Admins can allow specific players to join a server without the permission or disable the protection of a server for all players via a command.
## Installation
1. Download the Plugin from the releases page
2. Put the CloudPermissionWhitelist.jar into [CloudNet Directory]/local/templates/Global/server/plugins/ (If the plugins folder does not exist create it).
3. Restart your services
## Commands
| Command | Permission | Description |
|--|--|--|
| `/allowtempjoin <Player> [Time]` | `cloudpermissionwhitelist.allowtempjoin` | Allows a player to join the server temporarily (time p = permanent) |
| `/denytempjoin <Player>` | `cloudpermissionwhitelist.denytempjoin` | Recalls the permission to enter the server temporarily |
| `/listtempjoin` | `cloudpermissionwhitelist.listtempjoin` | Lists all players that can join temporary |
| `/denyalltempjoin` | `cloudpermissionwhitelist.denyalltempjoin` | Recalls the permission to enter the server temporarily for all players |
| `/localkick <Player> [Reason]` | `cloudpermissionwhitelist.localkick` | Kick a player only from the current server |
| `/joinprotection <status/on/off> [Time]` | `cloudpermissionwhitelist.joinprotection` | Enable/disable the whitelist (optional for a specific time) |
## Permissions
| Permission | Description |
|--|--|
| `cloudpermissionwhitelist.join.*` | Allow to join services of all tasks |
| `cloudpermissionwhitelist.join.<TaskName>` | Allow to join services of a specific task |
| `cloudpermissionwhitelist.*` | Wildcard permission (All permissions) |

The command permissions are listed at the command section.

Users with the * permission usually also have permission to join all task's services.
## Config
| Option | Type | Description |
|--|--|--|
| `updateCheck` --> `check` | BOOLEAN | Check for updates |
| `updateCheck` --> `notifyConsole` | BOOLEAN | Send a message to the console if a new update is available |
| `updateCheck` --> `notifyPlayer` | BOOLEAN | Notify admins on join that a new update is available |
| `tempJoinTime` | INT | The time (in seconds) a temporarily allowed player has time to join the server (Default: 300, min: 1, max: 1800) |
| `autoDisableWhitelist` | BOOLEAN | With this option enabled, the server will automatically disable the vanilla whitelist when the plugin is started (Default: true) |
| `enforceWhitelist` | BOOLEAN | If enabled, CloudPermissionWhitelist will check if players that are already on the server have the join permission and kick them if they don't (Default: false)  |
| `statsTracking` | BOOLEAN | If enabled, the plugin will report to a server that it exists. This helps to see how many users are currently using the plugin. Please enable it (it's disabled by default) (Default: false) |
## Version support for lower versions
Officially, only 1.13 and higher is supported.
If the plugin is needed for a lower Minecraft version, open the JAR archive with e.g. 7-zip and set the api-version item in the plugin.yml file to the desired version. However, this is not supported and errors may occur.
## Bugs
Create an issue on GitHub instead of writing a bad review.
## Some other stuff
[![CodeFactor](https://www.codefactor.io/repository/github/jandie1505/cloudpermissionwhitelist/badge)](https://www.codefactor.io/repository/github/jandie1505/cloudpermissionwhitelist)
