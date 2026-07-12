package com.xzavier0722.mc.plugin.slimefun4.storage.util;

import city.norain.slimefun4.utils.StringUtil;
import com.xzavier0722.mc.plugin.slimefun4.storage.controller.StorageType;
import io.github.thebusybiscuit.slimefun4.core.debug.Debug;
import io.github.thebusybiscuit.slimefun4.core.debug.TestCase;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.logging.Level;
import javax.annotation.Nullable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

public class DataUtils {
    /**
     * 使用 {@link BukkitObjectOutputStream} 序列化 {@link ItemStack}
     * 為 Base64 字符串，用於數據庫存儲.
     *
     * @param itemStack 要序列化的 {@link ItemStack}
     * @return 序列化後的 Base64 字符串
     */
    public static String serializeItemStack(ItemStack itemStack) {
        Debug.log(TestCase.BACKPACK, "Serializing itemstack: " + itemStack);

        if (itemStack == null) {
            return "";
        }

        try (var stream = new ByteArrayOutputStream();
                var bs = new BukkitObjectOutputStream(stream)) {
            bs.writeObject(itemStack);
            var itemStr = Base64.getEncoder().encodeToString(stream.toByteArray());

            if (!Slimefun.getConfigManager().isBypassItemLengthCheck()
                    && Slimefun.getDatabaseManager().getBlockDataStorageType() == StorageType.MYSQL
                    && itemStr.length() > 65535) {

                throw new IllegalArgumentException("檢測到過大物品, 請聯繫物品對應插件開發者解決: " + StringUtil.itemStackToString(itemStack)
                        + ", size = " + itemStr.length());
            }

            return itemStr;
        } catch (Throwable e) {
            Slimefun.logger().log(Level.SEVERE, "序列化物品時出現錯誤, 將存儲空值", e);
            return "";
        }
    }

    /**
     * 使用 {@link BukkitObjectInputStream} 反序列化 Base64 字符串
     * 為 {@link ItemStack} 對象.
     *
     * @param base64Str 要反序列化的 Base64 字符串
     * @return 反序列化後的 {@link ItemStack} 對象
     */
    @Nullable public static ItemStack deserializeItemStack(String base64Str) {
        if (base64Str == null || base64Str.isEmpty() || base64Str.isBlank()) {
            return null;
        }

        Debug.log(TestCase.BACKPACK, "Deserializing itemstack: " + base64Str);

        try (var stream = new ByteArrayInputStream(Base64.getMimeDecoder().decode(base64Str));
                var bs = new BukkitObjectInputStream(stream)) {
            var result = (ItemStack) bs.readObject();

            Debug.log(TestCase.BACKPACK, "Deserialized itemstack: " + result);

            if (result.getType().isAir()) {
                Slimefun.logger().log(Level.SEVERE, "反序列化數據庫中的物品失敗! 對應物品無法顯示.");
            }

            return result;
        } catch (Exception ex) {
            throw new RuntimeException("反序列化物品時出現錯誤, 對應物品無法顯示", ex);
        }
    }

    public static String blockDataBase64(String text) {
        return Slimefun.getDatabaseManager().isBlockDataBase64Enabled() ? base64Encode(text) : text;
    }

    public static String blockDataDebase64(String base64Str) {
        return Slimefun.getDatabaseManager().isBlockDataBase64Enabled() ? base64Decode(base64Str) : base64Str;
    }

    public static String profileDataBase64(String text) {
        return Slimefun.getDatabaseManager().isProfileDataBase64Enabled() ? base64Encode(text) : text;
    }

    public static String profileDataDebase64(String base64Str) {
        return Slimefun.getDatabaseManager().isProfileDataBase64Enabled() ? base64Decode(base64Str) : base64Str;
    }

    public static String base64Encode(String text) {
        return Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
    }

    public static String base64Decode(String base64Str) {
        return new String(Base64.getDecoder().decode(base64Str), StandardCharsets.UTF_8);
    }
}
