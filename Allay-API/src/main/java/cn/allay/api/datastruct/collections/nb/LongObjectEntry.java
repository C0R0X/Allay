package cn.allay.api.datastruct.collections.nb;

import java.util.Map;

/**
 * Allay Project 2023/7/8
 *
 * @author PowerNukkitX
 */
public interface LongObjectEntry<V> extends Map.Entry<Long, V> {
    @Deprecated
    default Long getKey() {
        return getLongKey();
    }

    long getLongKey();
}