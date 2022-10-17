package net.jandie1505.CloudPermissionWhitelist.misc;

import net.jandie1505.CloudPermissionWhitelist.CloudPermissionWhitelist;
import net.jandie1505.githubreleasesupdatecheck.GithubReleasesUpdateCheck;

public class UpdateChecker {
    private final CloudPermissionWhitelist cloudPermissionWhitelist;

    public UpdateChecker(CloudPermissionWhitelist cloudPermissionWhitelist) {
        this.cloudPermissionWhitelist = cloudPermissionWhitelist;
    }

    private boolean updateAvailable = false;

    public void refreshUpdateStatus() {
        updateAvailable = GithubReleasesUpdateCheck.checkForUpdate("jandie1505", "CloudPermissionWhitelist", this.cloudPermissionWhitelist.getDescription().getVersion());
    }

    public boolean isUpdateAvailable() {
        return this.updateAvailable;
    }
}
