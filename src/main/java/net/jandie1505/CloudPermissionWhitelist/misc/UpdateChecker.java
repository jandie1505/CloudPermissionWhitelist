package net.jandie1505.CloudPermissionWhitelist.misc;

import net.jandie1505.CloudPermissionWhitelist.CloudPermissionWhitelist;
import net.jandie1505.githubreleasesupdatecheck.GithubReleasesUpdateCheck;

public class UpdateChecker {
    private static boolean updateAvailable = false;

    public static void refreshUpdateStatus() {
        updateAvailable = GithubReleasesUpdateCheck.checkForUpdate("jandie1505", "CloudPermissionWhitelist", CloudPermissionWhitelist.getPlugin().getDescription().getVersion());
    }

    public static boolean isUpdateAvailable() {
        return updateAvailable;
    }
}
