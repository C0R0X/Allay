package org.allaymc.server.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.allaymc.server.Allay;

import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Properties;

/**
 * @author daoge_cmd
 */
@UtilityClass
public final class GitProperties {

    private static final Properties GIT_PROPERTIES = init();

    @SneakyThrows
    private static Properties init() {
        var properties = new Properties();
        try (var inputStreamReader = new InputStreamReader(Objects.requireNonNull(Allay.EXTRA_RESOURCE_CLASS_LOADER.getResourceAsStream("git.properties")))) {
            properties.load(inputStreamReader);
        }
        return properties;
    }

    public static String getBranch() {
        return GIT_PROPERTIES.getProperty("git.branch");
    }

    public static String getBuildHost() {
        return GIT_PROPERTIES.getProperty("git.build.host");
    }

    public static String getBuildUserEmail() {
        return GIT_PROPERTIES.getProperty("git.build.user.email");
    }

    public static String getBuildUserName() {
        return GIT_PROPERTIES.getProperty("git.build.user.name");
    }

    // Version of allay-server
    public static String getBuildVersion() {
        return GIT_PROPERTIES.getProperty("git.build.version");
    }

    // Version of allay-api
    public static String getBuildApiVersion() {
        return GIT_PROPERTIES.getProperty("git.build.api_version");
    }

    public static String getClosestTagCommitCount() {
        return GIT_PROPERTIES.getProperty("git.closest.tag.commit.count");
    }

    public static String getClosestTagName() {
        return GIT_PROPERTIES.getProperty("git.closest.tag.name");
    }

    public static String getCommitId() {
        return GIT_PROPERTIES.getProperty("git.commit.id");
    }

    public static String getCommitIdAbbrev() {
        return GIT_PROPERTIES.getProperty("git.commit.id.abbrev");
    }

    public static String getCommitIdDescribe() {
        return GIT_PROPERTIES.getProperty("git.commit.id.describe");
    }

    public static String getCommitMessageFull() {
        return GIT_PROPERTIES.getProperty("git.commit.message.full");
    }

    public static String getCommitMessageShort() {
        return GIT_PROPERTIES.getProperty("git.commit.message.short");
    }

    public static String getCommitTime() {
        return GIT_PROPERTIES.getProperty("git.commit.time");
    }

    public static String getCommitUserEmail() {
        return GIT_PROPERTIES.getProperty("git.commit.user.email");
    }

    public static String getCommitUserName() {
        return GIT_PROPERTIES.getProperty("git.commit.user.name");
    }

    public static String getDirty() {
        return GIT_PROPERTIES.getProperty("git.dirty");
    }

    public static String getRemoteOriginUrl() {
        return GIT_PROPERTIES.getProperty("git.remote.origin.url");
    }

    public static String getTags() {
        return GIT_PROPERTIES.getProperty("git.tags");
    }

    public static String getTotalCommitCount() {
        return GIT_PROPERTIES.getProperty("git.total.commit.count");
    }

    public static boolean isDevBuild() {
        return GIT_PROPERTIES.getProperty("git.build.is_dev_build").equals("true");
    }
}
