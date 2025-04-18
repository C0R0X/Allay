package org.allaymc.api.world;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.CustomKey;
import eu.okaeri.configs.annotation.Exclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * WorldSettings contains the settings of all worlds in the server.
 *
 * @author daoge_cmd
 */
@Getter
@Accessors(fluent = true)
public class WorldSettings extends OkaeriConfig {
    @Exclude
    public static final String DEFAULT_WORLD_NAME = "world";
    @Exclude
    public static final WorldSetting DEFAULT = WorldSetting.builder()
            .storageType("LEVELDB")
            .overworld(new WorldSetting.DimensionSettings("FLAT", ""))
            .build();

    @CustomKey("worlds")
    private Map<String, WorldSetting> worlds = Map.of(DEFAULT_WORLD_NAME, DEFAULT);

    @CustomKey("default-world")
    @Setter
    private String defaultWorld = DEFAULT_WORLD_NAME;

    @Builder
    @Getter
    @Accessors(fluent = true)
    public static class WorldSetting extends OkaeriConfig {
        @Setter
        @Comment("If set to false, the world will not be loaded")
        @CustomKey("enable")
        @Builder.Default
        private boolean enable = true;

        @Setter
        @Comment("If set to true, the information of this world will not be saved to world-settings.yml,")
        @Comment("therefore it won't be loaded after the server restarted. This is useful for world created")
        @Comment("for game room by plugin and will be deleted when shutdown")
        @CustomKey("runtime-only")
        private boolean runtimeOnly;

        @CustomKey("storage-type")
        private String storageType;
        private DimensionSettings overworld;

        @Comment("If you don't want to have nether / the end in this world, left it null")
        private DimensionSettings nether;

        @CustomKey("the-end")
        private DimensionSettings theEnd;

        @AllArgsConstructor
        @Getter
        @Accessors(fluent = true)
        @Builder
        public static class DimensionSettings extends OkaeriConfig {
            @CustomKey("generator-type")
            @Builder.Default
            private String generatorType = "VOID";

            @CustomKey("generator-preset")
            @Builder.Default
            private String generatorPreset = "";
        }
    }
}
