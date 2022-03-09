# CloudPermissionWhitelist
A plugin for the CloudNet v3 cloud system, which ensures that you can only join certain tasks with a certain permission.
## Installation
1. Download the Plugin from the releases page
2. Put the CloudPermissionWhitelist.jar into [CloudNet Directory]/local/templates/Global/server/plugins/ (If the plugins folder does not exist create it).
3. Restart your services
## Commands
| Command | Permission | Description |
|--|--|--|
| /allowtempjoin [Player] | cloudpermissionwhitelist.allowtempjoin | Allows a player to join the server for 5 minutes |
| /denytempjoin [Player] | cloudpermissionwhitelist.denytempjoin | Recalls the permission to enter the server temporarily |
| /listtempjoin | cloudpermissionwhitelist.listtempjoin | Lists all players that can join temporary |
| /denyalltempjoin | cloudpermissionwhitelist.denyalltempjoin | Recalls the permission to enter the server temorarily for all players |
| /localkick [Player] | cloudpermissionwhitelist.localkick | Allows to kick players via the /localkick command |

## Permissions
| Permission | Description |
|--|--|
| cloudpermissionwhitelist.join.* | Allow to join all task's services |
| cloudpermissionwhitelist.join.[TaskName] | Allow to join a specific task's services |
| cloudpermissionwhitelist.* | Wildcard permission (All permissions) |

The command permissions are listed at the command section.

Users with the * permission usually also have permission to join all task's services.
## Version support for lower versions
Officially, only 1.13 and higher is supported.
If the plugin is needed for a lower Minecraft version, open the JAR archive with e.g. 7-zip and set the api-version item in the plugin.yml file to the desired version. However, this is not supported and errors may occur.
## Bugs
Create an issue on GitHub instead of writing a bad review.
