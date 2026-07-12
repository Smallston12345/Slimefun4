package com.xzavier0722.mc.plugin.slimefun4.storage.common;

/**
 * {@link DataType} 是 Slimefun 數據庫控制器的類型，
 * 用於區分不同的數據存儲類型。
 */
public enum DataType {
    /**
     * 玩家檔案，通常包含研究進度、揹包等其他玩家相關數據。
     */
    PLAYER_PROFILE,

    /**
     * Slimefun 方塊數據
     */
    BLOCK_STORAGE
}
