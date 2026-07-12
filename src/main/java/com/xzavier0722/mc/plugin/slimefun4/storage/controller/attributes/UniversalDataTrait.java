package com.xzavier0722.mc.plugin.slimefun4.storage.controller.attributes;

import city.norain.slimefun4.api.menu.UniversalMenu;
import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunUniversalBlockData;
import com.xzavier0722.mc.plugin.slimefun4.storage.controller.SlimefunUniversalData;
import lombok.Getter;

/**
 * 這個枚舉類用於聲明 {@link SlimefunUniversalData} 的特徵.
 * 一個通用數據可以有單個或多個特徵.
 * <p>
 * 對於一個通用數據, 它默認擁有作為 k-v 容器的特徵.
 *
 * @see SlimefunUniversalData
 * @see SlimefunUniversalBlockData
 */
@Getter
public enum UniversalDataTrait {
    /**
     * BLOCK 特徵標明該通用數據屬於 {@link SlimefunUniversalBlockData}
     */
    BLOCK("location"),

    /**
     * INVENTORY 特徵標明該通用數據擁有一個 {@link UniversalMenu}
     */
    INVENTORY("");

    private final String reservedKey;

    UniversalDataTrait(String reservedKey) {
        this.reservedKey = reservedKey;
    }

    public static boolean isReservedKey(String key) {
        for (UniversalDataTrait trait : UniversalDataTrait.values()) {
            if (trait.getReservedKey().equals(key)) {
                return true;
            }
        }

        return false;
    }
}
